def buildJar() {
    echo "building the application..."
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t hanaghz/demo-app::${IMAGE_NAME}.'
        sh "echo $PASS | docker login -u $USER -p"
        sh 'docker push hanaghz/demo-app::${IMAGE_NAME}'
    }
}

def deployApp() {
    echo 'deploying the application...'
}

return this