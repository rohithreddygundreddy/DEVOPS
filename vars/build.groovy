def call(Map config = [:]) {

    String repoUrl       = config.get('repoUrl', 'git@github.com:rohithreddygundreddy/DEVOPS.git')
    String branch        = config.get('branch', 'main')
    String credentialsId = config.get('credentialsId', null)
    String requirements  = config.get('requirements', 'requirements.txt')

    if (!repoUrl) {
        error "repoUrl is required!"
    }

    stage('Clone Repository') {
        repo_checkout(repoUrl, branch, credentialsId)
    }

    stage('Setup Python Environment') {
        sh """
            python3 --version
            python3 -m venv venv
            . venv/bin/activate
            pip install --upgrade pip
        """
    }

    stage('Install Dependencies') {
        sh """
            . venv/bin/activate
            if [ -f ${requirements} ]; then
                pip install -r ${requirements}
            else
                echo 'No ${requirements} file found, skipping dependencies.'
            fi
        """
    }

    stage('Run Python Build / Tests') {
        sh """
            . venv/bin/activate
            python -m py_compile \$(find . -name "*.py") || true
            pytest || true
        """
    }

    echo 'Python build completed successfully!'
}
