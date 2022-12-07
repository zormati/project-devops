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
    sh 'mvn clean verify sonar:sonar  -Dmaven.test.skip=true -Dsonar.projectKey=project-devops   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=sqp_87fbbeac84a99062146b621a626bd8ae19a0430e'
}


def deployApp() {
    echo 'deploying the application...'
}

return this