pipeline {
    agent any

    environment {
        JIRA_SITE = 'shammeer.atlassian.net'
        TARGET_EMAIL = 'mohammedshammeer.s@gmail.com'
    }

    parameters {
        string(name: 'JIRA_ISSUE_KEY', defaultValue: 'PROJ-123', description: 'Associated Jira Task/Defect')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Notify Jira: Build Started') {
            steps {
                jiraComment(
                    site: "${JIRA_SITE}",
                    issueKey: "${params.JIRA_ISSUE_KEY}",
                    body: "Execution started for Build #${env.BUILD_NUMBER}.\nConsole URL: ${env.BUILD_URL}"
                )
                jiraTransitionIssue(
                    site: "${JIRA_SITE}",
                    issueKey: "${params.JIRA_ISSUE_KEY}",
                    transition: 'In Progress'
                )
            }
        }

        stage('Execute Test Suite') {
            steps {
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

            emailext(
                to: "${TARGET_EMAIL}",
                subject: "[Jenkins] ${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                body: '${JELLY_SCRIPT,template="html"}',
                mimeType: 'text/html',
                attachLog: true
            )
        }
        success {
            jiraComment(
                site: "${JIRA_SITE}",
                issueKey: "${params.JIRA_ISSUE_KEY}",
                body: "Build #${env.BUILD_NUMBER} executed successfully. All tests passed.\nReport: ${env.BUILD_URL}/testReport"
            )
            jiraTransitionIssue(
                site: "${JIRA_SITE}",
                issueKey: "${params.JIRA_ISSUE_KEY}",
                transition: 'Done'
            )
        }
        failure {
            jiraComment(
                site: "${JIRA_SITE}",
                issueKey: "${params.JIRA_ISSUE_KEY}",
                body: "Build #${env.BUILD_NUMBER} encountered failures. Review required.\nConsole: ${env.BUILD_URL}console"
            )
            jiraTransitionIssue(
                site: "${JIRA_SITE}",
                issueKey: "${params.JIRA_ISSUE_KEY}",
                transition: 'To Do'
            )
        }
        aborted {
            jiraComment(
                site: "${JIRA_SITE}",
                issueKey: "${params.JIRA_ISSUE_KEY}",
                body: "Build #${env.BUILD_NUMBER} was aborted manually or by system timeout."
            )
        }
    }
}