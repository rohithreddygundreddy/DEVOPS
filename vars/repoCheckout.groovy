def call(map config =[:]){
    def repoUrl=config.repoUrldef
    def branch=config.get('branch','main')
    def credentialsId=config.get('credentialsId','')
    def dirName=config.get('dir','')
    if (!repoUrl){
        error 'repoCheckout: 'repoUrl' is requred'
    }

    if (dirName){
        dir(dirName){
            checkOutRepo(repoUrl, branch, credentialsId)
        }
    }else{
        checkoutRepo(repoUrl, branch, credentialsId)
    }
}

private void checkoutRepo(Stirng repoUrl, String branch, String credentialsId){
    checkout([
        $class: 'GitSCM',
        branch: [[name: '*/${branch}']],
        userRemoteconfigs: [[
            url: repoUrl,
            credentialsId:credentialsId
        ]]
    ])
}