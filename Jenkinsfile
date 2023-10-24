pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Gateway'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    cd Gateway
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    cd server
                    docker stop Payment-Service || true
                    docker rm Payment-Service || true
                    docker rmi Payment-Service-Img || true
                    docker build -t Payment-Service-Img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name Payment-Service -p 8080:8000 Payment-Service-Img'
            }
        }
    }
}
