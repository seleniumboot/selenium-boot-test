// Jenkins pipeline for the Selenium Boot consumer test suite.
//
// Runs on every push to THIS repo (selenium-boot-test) and — when triggered with
// BUILD_FRAMEWORK_FROM_SOURCE=true — also validates a freshly-pushed build of the
// selenium-boot framework BEFORE it is published to Maven Central.
//
// Triggering:
//   • This repo:      GitHub webhook + pollSCM safety net (see triggers{} below).
//   • Framework repo: have the selenium-boot pipeline trigger this job downstream:
//
//       build job: 'selenium-boot-test',
//             parameters: [booleanParam(name: 'BUILD_FRAMEWORK_FROM_SOURCE', value: true)],
//             wait: false
//
//     (Replace 'selenium-boot-test' with this job's actual name in Jenkins.)
//
// Required Jenkins config:
//   • JDK named 'JDK17' and Maven named 'Maven3' under Global Tool Configuration.
//   • Chrome/Chromium installed on the agent (Selenium Manager fetches the driver).
//   • HTML Publisher plugin (for the publishHTML step).

pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    parameters {
        booleanParam(
            name: 'BUILD_FRAMEWORK_FROM_SOURCE',
            defaultValue: false,
            description: 'Checkout & build selenium-boot from source, then run this suite against it. ' +
                         'Framework-repo triggers set this to true; manual/consumer builds leave it false ' +
                         'to use the version pinned in pom.xml.'
        )
        string(
            name: 'FRAMEWORK_BRANCH',
            defaultValue: 'master',
            description: 'Branch of the selenium-boot framework to build when BUILD_FRAMEWORK_FROM_SOURCE is true.'
        )
    }

    environment {
        // Selenium Boot detects JENKINS_URL / CI and auto-enables headless mode.
        CI = 'true'
        FRAMEWORK_REPO_URL = 'https://github.com/seleniumboot/selenium-boot.git'
    }

    options {
        timestamps()
        timeout(time: 60, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    triggers {
        // Webhook gives instant builds (enable "GitHub hook trigger for GITScm polling"
        // on this job and add a push webhook to the repo). pollSCM is the fallback that
        // catches any push whose webhook was missed.
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build framework from source') {
            when { expression { return params.BUILD_FRAMEWORK_FROM_SOURCE } }
            steps {
                dir('selenium-boot-framework') {
                    git url: env.FRAMEWORK_REPO_URL, branch: params.FRAMEWORK_BRANCH
                    script {
                        // Read the framework's project version (first <version> in its pom).
                        env.FRAMEWORK_VERSION = sh(
                            script: "grep -m1 '<version>' pom.xml | sed -E 's/.*<version>(.*)<\\/version>.*/\\1/' | tr -d '[:space:]'",
                            returnStdout: true
                        ).trim()
                    }
                    echo "Building selenium-boot ${env.FRAMEWORK_VERSION} from source (branch: ${params.FRAMEWORK_BRANCH})"
                    sh 'mvn clean install -DskipTests -B --no-transfer-progress'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // When built from source, pin the suite to the freshly-installed version.
                    def versionOverride = (params.BUILD_FRAMEWORK_FROM_SOURCE && env.FRAMEWORK_VERSION)
                            ? "-Dselenium-boot.version=${env.FRAMEWORK_VERSION}"
                            : ''
                    sh "mvn clean test -B --no-transfer-progress ${versionOverride}"
                }
            }
            post {
                always {
                    // TestNG (surefire) + JUnit 5 (failsafe) result XML.
                    junit testResults: 'target/surefire-reports/TEST-*.xml, target/failsafe-reports/TEST-*.xml',
                          allowEmptyResults: true
                    archiveArtifacts artifacts: 'target/selenium-boot-report.html, target/selenium-boot-metrics.json, target/metrics-history/**, target/traces/**, target/recordings/**',
                                     allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            publishHTML(target: [
                allowMissing         : true,
                alwaysLinkToLastBuild: true,
                keepAll              : true,
                reportDir            : 'target',
                reportFiles          : 'selenium-boot-report.html',
                reportName           : 'Selenium Boot Report'
            ])
            echo "Build finished: ${currentBuild.currentResult}"
        }
        failure {
            echo 'Tests failed — open the Selenium Boot Report or target/surefire-reports.'
        }
        success {
            echo 'All tests passed.'
        }
    }
}
