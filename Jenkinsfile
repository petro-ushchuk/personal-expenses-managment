pipeline {

    agent any

    options {
        timestamps()
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
                sh './gradlew assemble docker'
            }
        }

        stage('Deploy to develop') {
            when {
                branch 'develop'
            }
            steps {
                sh './gradlew assemble docker dockerRun'
            }
        }
    }

//    post {
//        failure {
//            mail to: 'petr.ushchuk@gmail.com',
//                    subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
//                    body: "Something is wrong with ${env.BUILD_URL}"
//        }
//    }
}