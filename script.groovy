def buildJar() {
    echo "building the application..."
    sh 'mvn clean install -Dmaven.test.skip=true'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t hanaghz/demo-app:${IMAGE_NAME} .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push hanaghz/demo-app:${IMAGE_NAME}'
    }
}

def sonarTest() {
    echo "Running sonarQube checks..."
    sh 'mvn clean verify sonar:sonar   -Dmaven.test.skip=true  -Dsonar.projectKey=project-devops   -Dsonar.host.url=http://172.17.0.2:9000   -Dsonar.login=sqp_114f7278795d345ede80d4d31a39c9bd382db6dd'
}


def deployApp() {
    echo 'deploying the application...'
}
//test
def pushToNexus() {
    echo "pushing the jar file to Nexus maven-snapshots repo..."
    sh 'mvn clean deploy -Dmaven.test.skip=true'
}


return this