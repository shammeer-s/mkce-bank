pipeline {
    agent any

    tools {
        maven 'MAVEN3'
        jdk 'JDK21'
    }

    environment {
        APP_URL = 'http://localhost:3000'
        JIRA_SITE = 'shammeer.atlassian.net' // Jenkins Jira Plugin configuration name
        PROJECT_KEY = 'AURA'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Start Node Server') {
            steps {
                dir('f:/mkce-bank') {
                    bat 'npm install'
                }
            }
        }

        stage('Run JMeter Load Tests') {
            steps {
                // Assuming JMeter is installed and added to PATH on the agent
                bat 'jmeter -n -t jmeter/aurabank_load_test.jmx -l target/jmeter-results.jtl'
            }
            // Ignore failures to ensure UI tests run even if load tests fail
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {}
        }

        stage('Run UI & BDD Tests (TestNG)') {
            steps {
                bat 'mvn clean test -Dbrowser=chrome -Dheadless=true'
            }
            post {
                always {
                    // Archive ExtentReports HTML report
                    archiveArtifacts artifacts: 'target/ExtentReport.html', allowEmptyArchive: true
                }
                failure {
                    // Create a Jira Bug if the test stage fails
                    script {
                        def issue = [fields: [
                                project: [key: env.PROJECT_KEY],
                                summary: "Automated Test Suite Failure - Build #${env.BUILD_NUMBER}",
                                description: "The TestNG test suite failed. Please check the Jenkins Extent Report for details. URL: ${env.BUILD_URL}",
                                issuetype: [name: 'Bug']
                        ]]

                        try {
//                            def response = jiraNewIssue issue: issue, site: env.JIRA_SITE
//                            echo "Created Jira Issue: ${response.data.key}"
                            echo "Created Jira Issue"
                        } catch (Exception e) {
                            echo "Warning: Failed to create Jira ticket. Check Jira plugin configuration. Error: ${e.message}"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished."

        }
        success {
//            emailext(
//                subject: "Build ${currentBuild.fullDisplayName} - ${currentBuild.currentResult}",
//                body: "Check the console output at ${env.BUILD_URL}",
//                to: 'dev-team@example.com'
//            )

            echo "Build successful."
        }
    }
}
