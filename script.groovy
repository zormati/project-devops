def buildJar() {
    echo "building the application..."
    sh 'mvn clean install -Dmaven.test.skip=true'
}

def runUnitTests() {
    echo "running the unit tests..."
    //sh 'mvn test'
 
    // docker.image('mysql:latest').withRun('-e "MYSQL_ROOT_PASSWORD=hanah" -e "MYSQL_DATABASE=school_library" -p 3310:3306 --name sql-sidecarr') { c ->
    //     /* Wait until mysql service is up */
    //     sh 'sleep 30'
    //     /* Run some tests which require MySQL */
    //     sh 'mvn test'
    // }
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t zormati/devops:spring .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push zormati/devops:spring'
    }
}

def sonarTest() {
    echo "Running sonarQube checks..."
    sh 'mvn dependency:resolve'
    sh 'mvn clean verify sonar:sonar  -Dmaven.test.skip=true  -Dsonar.projectKey=testsonar   -Dsonar.host.url=http://172.18.0.4:9000  -Dsonar.login=sqp_31cd7b0b531989072ed3a68aa0966bd2491376a2'
}


def deployApp() {
    echo 'deploying the application...'
}

def pushToNexus() {
    echo "pushing the jar file to Nexus maven-snapshots repo..."
    // sh 'mvn dependency:resolve'
    withCredentials([usernamePassword(credentialsId: 'nexus-cred', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'mvn clean deploy -Dmaven.test.skip=true'
    }
}


return this