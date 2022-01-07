pipeline {

    agent {
        docker {
            image 'openjdk:11'
            args '-v /tmp:/tmp'
            reuseNode true
        }
    }

    triggers {
        bitbucketPush()
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

        stage ('Deploy') {
            steps {
                sh 'docker run -d -p 8080:8080 ${IMAGE}'
            }
        }
    }

    post {
        failure {
            // notify users when the Pipeline fails
            mail to: 'petro.ushchuk@team-inspirit.com',
                    subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                    body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
}