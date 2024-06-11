pipeline {
    agent any

    environment {
        VERSION = "${env.BUILD_ID}"
    }

    tools {
        maven "Maven"
    }

    stages {
        stage('Debug Credentials') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_CREDENTIAL', passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW', usernameVariable: 'DOCKERHUB_CREDENTIALS_USR')]) {
                        echo "DockerHub Username: ${DOCKERHUB_CREDENTIALS_USR}"
                        echo "DockerHub Password: ${DOCKERHUB_CREDENTIALS_PSW}"
                    }
                }
            }
        }

        stage('Docker Login Test') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_CREDENTIAL', passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW', usernameVariable: 'DOCKERHUB_CREDENTIALS_USR')]) {
                        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    }
                }
            }
        }

        stage('Maven Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://3.101.143.247:9000/ -Dsonar.login=squ_a80c9f4238bcaf5fd5ccee287a41f79fb3b393d2'
                }
            }
        }

        stage('Check code coverage') {
            steps {
                script {
                    def token = "squ_a80c9f4238bcaf5fd5ccee287a41f79fb3b393d2"
                    def sonarQubeUrl = "http://3.101.143.247:9000/api"
                    def componentKey = "com.codedecode:order"
                    def coverageThreshold = 0.0

                    def response = sh(
                        script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                        returnStdout: true
                    ).trim()

                    def coverage = sh(
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                    echo "Coverage: ${coverage}"

                    if (coverage < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_CREDENTIAL', passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW', usernameVariable: 'DOCKERHUB_CREDENTIALS_USR')]) {
                        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                        sh 'docker build -t canbeaudocker/restaurant-listing-service:${VERSION} .'
                        sh 'docker push canbeaudocker/restaurant-listing-service:${VERSION}'
                    }
                }
            }
        }

        stage('Cleanup Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Update Image Tag in GitOps') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/main']],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        userRemoteConfigs: [[
                            url: 'git@github.com:Gitcanbeau/67_deployment_service.git',
                            credentialsId: 'git-ssh'
                        ]]
                    ])
                    // Update the image tag in the manifest file
                    sh 'sed -i \'s/image:.*/image: canbeaudocker\\/order:${VERSION}/\' aws/order-manifest.yml'
                    sh 'git add aws/order-manifest.yml'
                    sh 'git commit -m "Update image tag to ${VERSION}"|| echo "No changes to commit"'

                    // Use sshagent to push changes
                    sshagent(credentials: ['git-ssh']) {
                        sh 'git push origin HEAD:main'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}

