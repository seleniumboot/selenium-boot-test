// Jenkins pipeline for the Selenium Boot consumer test suite.
//
// Triggers on every push to this repo (githubPush webhook). It can also be
// triggered downstream by the selenium-boot framework job with
// BUILD_FRAMEWORK_FROM_SOURCE=true, which builds the framework from source and
// runs this suite against it — validating unpublished changes before release.
//
// Jenkins prerequisites:
//   • Maven tool named "Maven-3" (Global Tool Configuration); agent default JDK is 17+.
//   • Chrome/Chromium on the agent (Selenium Manager fetches the driver).
//   • HTML Publisher plugin (for the publishHTML step).

pipeline {
    agent any

    triggers {
        githubPush()
    }

    tools {
        maven "Maven-3"
    }

    parameters {
        booleanParam(
            name: 'BUILD_FRAMEWORK_FROM_SOURCE',
            defaultValue: false,
            description: 'Build selenium-boot from source and test against it. ' +
                         'Framework-repo triggers set this true; normal pushes leave it false ' +
                         'to use the version pinned in pom.xml.'
        )
        string(
            name: 'FRAMEWORK_BRANCH',
            defaultValue: 'master',
            description: 'selenium-boot branch to build when BUILD_FRAMEWORK_FROM_SOURCE is true.'
        )
    }

    environment {
        // Selenium Boot detects JENKINS_URL / CI and auto-enables headless mode.
        CI = 'true'
        FRAMEWORK_REPO_URL = 'https://github.com/seleniumboot/selenium-boot.git'
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/seleniumboot/selenium-boot-test.git'
            }
        }

        stage('Build framework from source') {
            when { expression { return params.BUILD_FRAMEWORK_FROM_SOURCE } }
            steps {
                dir('selenium-boot-framework') {
                    git url: env.FRAMEWORK_REPO_URL, branch: params.FRAMEWORK_BRANCH
                    script {
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
                    def versionOverride = (params.BUILD_FRAMEWORK_FROM_SOURCE && env.FRAMEWORK_VERSION)
                            ? "-Dselenium-boot.version=${env.FRAMEWORK_VERSION}"
                            : ''
                    sh "mvn -Dmaven.test.failure.ignore=true ${versionOverride} clean package"
                }
            }
        }
    }

    post {
        always {
            // TestNG (surefire) + JUnit 5 (failsafe) results — runs regardless of outcome.
            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/TEST-*.xml, **/target/failsafe-reports/TEST-*.xml'

            // Publish the Selenium Boot HTML report (requires HTML Publisher plugin).
            publishHTML(target: [
                allowMissing         : true,
                alwaysLinkToLastBuild: true,
                keepAll              : true,
                reportDir            : 'target',
                reportFiles          : 'selenium-boot-report.html',
                reportName           : 'Selenium Boot Report'
            ])
        }
        success {
            archiveArtifacts 'target/*.jar'
        }
    }
}
