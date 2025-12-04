// vars/repoCheckout.groovy
def call(String repoUrl, String branch = 'main', String credentialsId = '') {
    // If credentialsId is empty, it will checkout public repo via HTTPS
    echo "[repoCheckout] Cloning ${repoUrl} (branch: ${branch})"

    def scmConfig = [
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[url: repoUrl]]
    ]

    if (credentialsId?.trim()) {
        scmConfig.userRemoteConfigs[0].credentialsId = credentialsId
    }

    checkout(scmConfig)
}
