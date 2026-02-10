pipeline {
    agent any

    tools {
        maven 'Maven'
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
                bat 'mvn clean test -Dbrowser=chrome'
            }
        }
    }

    post {
        always {
            // Publish Allure Report
            allure(
                includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            )

            // Optional: keep raw results
            archiveArtifacts artifacts: 'target/allure-results/**/*', allowEmptyArchive: true
        }
    }
}
