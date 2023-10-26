pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'jenkins-github-access-token', url:'https://github.com/Spharos-final-project-WOOYANO/Payment'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    docker stop payment-service || true
                    docker rm payment-service || true
                    docker rmi payment-service-img || true
                    docker build -t payment-service-img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name payment-service -p 8007:8000 payment-service-img'
            }
        }
    }
}
