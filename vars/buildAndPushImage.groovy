// vars/buildAndPushImage.groovy
def call(Map config = [:]) {
    String awsRegion      = config.awsRegion      ?: 'ap-south-1'
    String ecrRepo        = config.ecrRepo        // e.g. 123456789012.dkr.ecr.ap-south-1.amazonaws.com/my-app
    String imageTag       = config.imageTag       ?: "build-${env.BUILD_NUMBER}"
    String awsCredentials = config.awsCredentials ?: 'aws-jenkins-creds' // Jenkins credential ID

    if (!ecrRepo) {
        error "[buildAndPushImage] ecrRepo is required"
    }

    String fullImage = "${ecrRepo}:${imageTag}"

    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: awsCredentials]]) {
        echo "[buildAndPushImage] Logging into ECR: ${ecrRepo}"

        sh """
            set -e
            aws configure set default.region ${awsRegion}
            aws ecr get-login-password --region ${awsRegion} \
              | docker login --username AWS --password-stdin ${ecrRepo.split('/')[0]}

            echo "[buildAndPushImage] Building Docker image ${fullImage}"
            docker build -t ${fullImage} .

            echo "[buildAndPushImage] Pushing Docker image ${fullImage}"
            docker push ${fullImage}
        """
    }

    // Return image reference so we can use it in deploy stage
    return fullImage
}
