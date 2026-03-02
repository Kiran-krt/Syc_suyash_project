pipeline {
    agent any

    tools {
        gradle 'Gradle7.6.3'
    }

    stages {
        stage('Build Test Env') {
            steps {
                sendNotification(
                    "#2A42EE",
                    "Build STARTED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)\n$changeString"
                )

                sh '''
                    cp src/main/resources/application.properties.example src/main/resources/application.properties
                    cp src/main/resources/application-notification.properties.example src/main/resources/application-notification.properties

                    set +e
                    rm ${TEST_PROPERTY_FILE}
                    set -e
                '''
                withGradle {
                    sh './gradlew clean build '
                }
            }
        }
        stage('Deploy Test Env') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: params.SERVER_NAME,
                            transfers: [
                                sshTransfer(
                                    cleanRemote: false,
                                    excludes: '',
                                    execCommand: 'cd ' + params.DEPLOY_PATH_TEST_ENV + '/artifacts' + ' && rm -rf ' + params.DEPLOY_JAR + '_bkp && mv ' + params.DEPLOY_JAR + ' ' + params.DEPLOY_JAR + '_bkp 2>/dev/null; true && mv ' + BUILD_JAR + ' ' + params.DEPLOY_JAR + ' 2>/dev/null; true',
                                    execTimeout: 120000,
                                    flatten: true,
                                    makeEmptyDirs: false,
                                    noDefaultExcludes: false,
                                    remoteDirectory: params.DEPLOY_PATH_TEST_ENV + '/artifacts/',
                                    remoteDirectorySDF: false, removePrefix: '',
                                    sourceFiles: params.BUILD_DIR + '/' + params.BUILD_JAR
                                )
                            ],
                            usePromotionTimestamp: false,
                            useWorkspaceInPromotion: false, verbose: true
                        )
                    ]
                )
                sh '''
                    echo logging.file.name=${LOG_FILE_PROPERTY} >> ${TEST_PROPERTY_FILE}
                    echo syc.swagger.server.urls=${SWAGGER_SERVER_URL} >> ${TEST_PROPERTY_FILE}
                    echo app.portal.url=${APP_PORTAL_URL} >> ${TEST_PROPERTY_FILE}
                    echo ${MULTI_TENANT_CONFIG_VALUES} | sed 's/ /\\n/g' >> ${TEST_PROPERTY_FILE}
                '''
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: params.SERVER_NAME,
                            transfers: [
                                sshTransfer(
                                    cleanRemote: false,
                                    excludes: '',
                                    execCommand: params.DEPLOY_COMMAND,
                                    execTimeout: 120000,
                                    flatten: true,
                                    makeEmptyDirs: false,
                                    noDefaultExcludes: false,
                                    remoteDirectory: params.DEPLOY_PATH_TEST_ENV + '/server-config/',
                                    remoteDirectorySDF: false,
                                    removePrefix: '',
                                    sourceFiles: params.TEST_PROPERTY_FILE
                                )
                            ],
                            usePromotionTimestamp: false,
                            useWorkspaceInPromotion: false,
                            verbose: true
                        )
                    ]
                )
            }
        }
    }

    post {
        aborted {
            sendNotification("#B2BEB5", "Build ABORTED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)")
        }

        success {
            sendNotification("#75F321", "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)")
        }

        failure {
            sendNotification("#E43E3E", "Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)")
        }

        unstable {
            sendNotification("#E4933E", "Build UNSTABLE: ${env.JOB_NAME} #${env.BUILD_NUMBER} (<${env.BUILD_URL}|Link to build>)")
        }
    }
}

def sendNotification(color, message) {
    mattermostSend(
            color: color,
            message: message,
            channel: "${MATTERMOST_CHANNEL_ID}",
            endpoint: "${MATTERMOST_WEBHOOK}"
    )
}

@NonCPS
def getChangeString() {
    MAX_MSG_LEN = 100
    def changeString = ""
    echo "Gathering SCM changes"
    def changeLogSets = currentBuild.rawBuild.changeSets
    for (int i = 0; i < changeLogSets.size(); i++) {
        def entries = changeLogSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            truncated_msg = entry.msg.take(MAX_MSG_LEN)
            changeString += " - ${truncated_msg} [${entry.author}]\n"
        }
    }
    if (!changeString) {
        changeString = " - No new changes"
    }
    return changeString
}
