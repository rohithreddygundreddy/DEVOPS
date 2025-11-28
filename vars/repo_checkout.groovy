def call(String repoUrl, string branch ='main', string credentialsId=null){
    if (credentialsId){
        checkout([
            $class: 'GitSCM',
            branches:[[name:branch]],
            userRemortConfigs:[[
                url:repoUrl,
                credentialsId: credentialsId
            ]]
        ])
    }
    else{
        git branch: branch, url:repoUrl
    }

}