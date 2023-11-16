pipeline {
    agent any
    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Payment'
            }
        }
	stage('Secret-File Download') {
	    steps {
	        withCredentials([
		    file(credentialsId:'Kafka-Secret-File', variable: 'kafkaSecret')
		    ])
	        {
		    sh "cp \$kafkaSecret ./src/main/resources/application-secret.yml"
		}
  	    }
	}
        stage('Build'){
            steps{
                script {
                    sh '''
                        pwd
                        chmod +x ./gradlew
                        ./gradlew build
                    '''

                }

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
                sh 'docker run --restart=always --network spharos-network -e EUREKA_URL="${EUREKA_URL}" -e KAFKA_URL1="${KAFKA_URL1}" -d --name payment-service payment-service-img'
            }
        }
    }
}

