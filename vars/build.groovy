def call(Map config=[:]){

    String repoUrl = config.get('repoUrl')
    String branch  = config.get('branch', 'main')
    String credentialsId = config.get('credentialsId', null)
    String requirements  = config.get('requirements', 'requirements.txt')

    stage('Clone Repository'){
        repo_checkout(repoUrl, branch, credentialsId)
    }

    stage('Setup Python Environment'){
        if (isUnix()) {
            sh """
                python3 --version
                python3 -m venv venv
                . venv/bin/activate
                pip install --upgrade pip
            """
        } else {
            bat """
                python --version
                python -m venv venv
                call venv\\Scripts\\activate
                pip install --upgrade pip
            """
        }
    }

    stage('Install Dependencies'){
        if (isUnix()) {
            sh """
                . venv/bin/activate
                pip install -r ${requirements}
            """
        } else {
            bat """
                call venv\\Scripts\\activate
                pip install -r ${requirements}
            """
        }
    }

    stage('Run Python Build / Tests'){
        if (isUnix()) {
            sh """
                . venv/bin/activate
                python -m py_compile \$(find . -name "*.py")
            """
        } else {
            bat """
                call venv\\Scripts\\activate
                python -m py_compile $(git ls-files "*.py")
            """
        }
    }

    echo "Python build completed successfully!"
}
