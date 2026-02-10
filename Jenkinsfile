pipeline {
    agent any

    tools {
        maven 'Maven'     // Jenkins → Global Tool Configuration
        jdk 'JDK17'
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
                // Explicitly tell Maven to use testng.xml
                bat '''
                    mvn clean test ^
                    -Dsurefire.suiteXmlFiles=testng.xml ^
                    -Dbrowser=chrome
                '''
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }

        stage('Publish JUnit Results') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-results/**/*', allowEmptyArchive: true
        }
    }
}
