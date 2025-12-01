// vars/repoCheckout.groovy

def call(Map config = [:]) {
    def repoUrl       = config.repoUrl
    def branch        = config.get('branch', 'main')
    def credentialsId = config.get('credentialsId', '')
    def dirName       = config.get('dir', '')

    if (!repoUrl) {
        error "repoCheckout: 'repoUrl' is required"
    }

    if (dirName) {
        dir(dirName) {
            checkoutRepo(repoUrl, branch, credentialsId)
        }
    } else {
        checkoutRepo(repoUrl, branch, credentialsId)
    }
}

private void checkoutRepo(String repoUrl, String branch, String credentialsId) {
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[
            url: repoUrl,
            credentialsId: credentialsId
        ]]
    ])
}
