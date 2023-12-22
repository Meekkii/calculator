pipeline {
    agent any

    stages {
        stage("Compilation") {
            steps {
                script {
                    sh "./gradlew compileJava"
                }
            }
        }
        stage("Test unitaire") {
            steps {
                script {
                    sh "./gradlew test"
                }
            }
        }
        stage("Couverture du code") {
            steps {
                script {
                    sh "./gradlew jacocoTestReport"
                    sh "./gradlew jacocoTestCoverageVerification"
                    publishHTML (target: [reportDir: 'build/reports/jacoco/test/html',reportFiles: 'index.html',
                    reportName: "JaCoCo Report"])
                }
            }
        }
        stage("Analyse statistique du code") {
            steps {
                script {
                    sh "./gradlew checkstyleMain"
                    publishHTML(target: [
                        reportDir: 'build/reports/checkstyle/',
                        reportFiles: 'main.html',
                        reportName: "Checkstyle Report"
                    ])
                }
            }
        }
        stage("Package") {
            steps {
                script {
                    sh "./gradlew build"
                }
            }
        }
        stage("Docker build") {
            steps {
                script {
                    sh "docker build -t calculator ."
                }
            }
        }
        stage("Docker push") {
            steps {
                script {
                    sh "docker push localhost:5000/calculator"
                }
            }
        }
        stage("Déploiement sur staging") {
            steps {
                script {
		    sh "docker container stop calculator"
                    sh "docker run -d -p 8765:8080 --name calculator --restart unless-stopped localhost:5000/calculator"
                }
            }
        }
        stage("Test d'acceptation") {
            steps {
                script {
                    sleep time: 5, unit: 'SECONDS'
                    sh "chmod +x acceptance_test.sh && ./acceptance_test.sh"
                }
            }
        }
    }

    post {
        always {
            script {
                mail to: 'ezamine92@gmail.com',
                subject: "Cher lion, votre compilation est terminée: ${currentBuild.fullDisplayName}",
                body: "Votre build est accompli. Veuillez vérifier: ${env.BUILD_URL}"
            }
        }
    }
}

