pipeline {
    agent any

    tools {
        maven 'Maven'     // must match Jenkins tool name
        jdk 'JDK17'       // must match Jenkins tool name
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Reddy062023/SeleniumFrameworkDesign.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test -Dbrowser=chrome'
            }
        }

        stage('Publish JUnit Report') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Publish HTML Report') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'TestNG HTML Report'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
        }
    }
}
