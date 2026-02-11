pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test -Dbrowser=chrome'
            }
        }

        stage('Publish TestNG Results') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']]
                ])
            }
        }
    }
    

    post {
        always {
            archiveArtifacts artifacts: 'target/**', allowEmptyArchive: true
        }
        success {
            echo '✅ Tests passed'
        }
        failure {
            echo '❌ Tests failed'
        }
    }
}
