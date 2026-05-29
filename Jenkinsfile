pipeline {
    agent any

    tools {
        maven 'maven-3.9.12'
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/rajaprasath21/RestAssuredAPIFramework.git'       
            }
        }

        stage('Clean Project') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Build Project') {
            steps {
                bat 'mvn compile'
            }
        }

        stage('Run API Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Generate Extent Report') {
            steps {
                echo 'Extent Report generated successfully.'
            }
        }

        stage('Publish Reports') {
            steps {

                // Publish Extent Report
                publishHTML(target: [
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'src/test/resources/ExtentReport',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent API Automation Report'
                ])

                // Publish TestNG/Surefire Reports
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {

        always {

            // Archive Extent Reports
            archiveArtifacts artifacts: 'src/test/resources/ExtentReport/*.html',
                              fingerprint: true

            // Archive logs if available
            archiveArtifacts artifacts: 'logs/*.log',
                              allowEmptyArchive: true
        }

        success {
            emailext(
                to: 'rajaprasath21@gmail.com',
                subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                body: """
                <html>
                <body>

                <h2 style="color:green;">API Automation Build Success ✅</h2>

                <p><b>Project:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                <p><b>Status:</b> SUCCESS</p>

                <p>
                <b>Build URL:</b>
                <a href="${env.BUILD_URL}">
                ${env.BUILD_URL}
                </a>
                </p>

                <p>
                <b>Extent Report:</b>
                <a href="${env.BUILD_URL}HTML_20Report/">
                View Report
                </a>
                </p>

                <br>
                <p>Regards,</p>
                <p><b>Automation Team</b></p>

                </body>
                </html>
                """,

                mimeType: 'text/html',
                attachLog: true
            )
        }

        failure {
            emailext(
                to: 'rajaprasath21@gmail.com',
                subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                body: """
                <html>
                <body>

                <h2 style="color:red;">API Automation Build Failed ❌</h2>

                <p><b>Project:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                <p><b>Status:</b> FAILED</p>

                <p>
                <b>Build URL:</b>
                <a href="${env.BUILD_URL}">
                ${env.BUILD_URL}
                </a>
                </p>

                <p>Please verify the console logs and failed test cases.</p>

                <br>
                <p>Regards,</p>
                <p><b>Automation Team</b></p>

                </body>
                </html>
                """,

                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}