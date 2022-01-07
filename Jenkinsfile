pipeline {

    agent any

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
                    reuseNode true
                }
            }
            steps {
                sh './gradlew clean test'
                junit '**/build/test-results/test/*.xml'
            }
        }

        stage('Build and Publish Image') {
            steps {
                sh """
                  docker build -t ${env.JOB_NAME} .
                  docker tag ${env.JOB_NAME} ${env.JOB_NAME}:${VERSION}
                  docker push ${env.JOB_NAME}:${VERSION}
                """
                sh "echo Image name - ${env.JOB_NAME}:${VERSION}"
            }
        }
//
//        stage('Deploy to develop') {
//            when {
//                branch 'develop'
//            }
//            steps {
//                sh 'docker run -d -p 80:8080 ${env.JOB_NAME}'
//            }
//        }
    }

//    post {
//        failure {
//            mail to: 'petr.ushchuk@gmail.com',
//                    subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
//                    body: "Something is wrong with ${env.BUILD_URL}"
//        }
//    }
}