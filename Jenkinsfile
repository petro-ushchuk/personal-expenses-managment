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

        stage('Test') {
            steps {
                sh './gradlew clean test'
                junit '**/build/test-results/test/*.xml'
            }
        }

        stage('Deploy') {
            when {
                branch 'develop'
            }
            steps {
                sh './gradlew assemble dockerStop docker dockerRun'
            }
        }
    }

    post {
        failure {
            mail to: 'petr.ushchuk@gmail.com',
                    subject: "Failed Pipseline: ${currentBuild.fullDisplayName}",
                    body: "Something is wrong with ${env.BUILD_URL}"
        }

        success {
            mail to: 'petr.ushchuk@gmail.com',
                    subject: "Pipeline Success: ${currentBuild.fullDisplayName}",
                    body: "Build success ${env.BUILD_URL}"
        }
    }
}