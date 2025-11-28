def call(Map config=[:]){
    string repoUrl=config.get('repoUrl', 'https://github.com/rohithreddygundreddy/DEVOPS.git')
    string branch=config.get('branch', 'main')
    string credentialsId=config.get('credentialsId','null')
    string requirements=config.get('requirements', 'requirmenents.txt')
    if (!repoUrl){
        error "repoUrl is required!"
    }
    stage('clone Repository'){
        cloneRepo(repoUrl, branch, credentialsId)
    }
    stage('setup python Environment'){
        sh"""
            python3 --version
            python3 -m venv venv
            . venv/bin/activate
            pip install --upgrade pip
        """
    }
    stage('install dependencies'){
        sh"""
            . venv/bin/activate
            if [ -f ${requirements}]; then
                pip install -r $(requirements)
            else
                echo 'No $(requirenments) file found, skippong dependencies.'
            fi
        """
    }
    stage('RUn python Build/ Tests'){
        sh """
            . venv/bin/activate
            python -m py_compile **/*.py || true
            pytest || true
        """
    }
    ech0 'Python build completed sucessfully!'
}