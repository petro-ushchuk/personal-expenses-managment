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
        IMAGE = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()
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
            }
        }

        stage('Build and Publish Image') {
            steps {
                sh """
                  docker build -t ${IMAGE} .
                  docker tag ${IMAGE} ${IMAGE}:${VERSION}
                  docker push ${IMAGE}:${VERSION}
                """
            }
        }

        stage ('Deploy to develop') {
            when {
                branch 'develop'
            }
            steps {
                sh 'docker run -d -p 80:8080 ${IMAGE}'
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