pipeline {

    agent {
        docker {
            image 'openjdk:11'
            args '-v /tmp:/tmp'
            reuseNode true
        }
    }

    options {
        timestamps()
    }

    environment {
        VERSION = sh script: "./gradlew version", returnStdout: true
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test and Build') {
            agent {
                docker {
                    image 'openjdk:11'
                    args '-v "$PWD":/app'
                    reuseNode true
                }
            }
            steps {
                sh './gradlew clean build'
                junit '**/target/*.xml'
            }
        }

        stage('Build and Publish Image') {
            steps {
                sh """
                  docker build -t ${env.JOB_NAME} .
                  docker tag ${env.JOB_NAME} ${env.JOB_NAME}:${VERSION}
                  docker push ${env.JOB_NAME}:${VERSION}
                """
            }
        }

        stage('Deploy to develop') {
            when {
                branch 'develop'
            }
            steps {
                sh 'docker run -d -p 80:8080 ${env.JOB_NAME}'
            }
        }
    }

    post {
        failure {
            mail to: 'petro.ushchuk@team-inspirit.com',
                    subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                    body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
}