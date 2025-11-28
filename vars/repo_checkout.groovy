def call(String repoUrl, String branch = 'main', String credentialsId = null) {

    stage("Validate Repository URL") {
        if (!repoUrl) {
            error "Repository URL is required!"
        }
    }

    stage("Checkout Repository") {
        if (credentialsId) {
            checkout([
                $class: 'GitSCM',
                branches: [[name: "*/${branch}"]],
                userRemoteConfigs: [[
                    url: repoUrl,
                    credentialsId: credentialsId
                ]]
            ])
        } else {
            git branch: branch, url: repoUrl
        }
    }
}
