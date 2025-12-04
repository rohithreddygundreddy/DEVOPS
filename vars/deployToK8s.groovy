// vars/deployToK8s.groovy
def call(Map config = [:]) {
    String kubeContext = config.kubeContext ?: ''
    String manifestDir = config.manifestDir ?: 'k8s'
    String image       = config.image       ?: ''

    echo "[deployToK8s] Deploying to Kubernetes"
    echo "[deployToK8s] Manifest directory: ${manifestDir}"

    sh """
        set -e
        if [ -n "${kubeContext}" ]; then
          echo "[deployToK8s] Using kubectl context ${kubeContext}"
          kubectl config use-context ${kubeContext}
        fi
    """

    if (image) {
        // Example: update image in a deployment using kubectl set image
        sh """
            echo "[deployToK8s] Updating image in deployment"
            # Adjust deployment name and container name
            kubectl set image deployment/my-app-deployment my-app-container=${image} --record
        """
    }

    // Optionally apply manifests (if using YAML files)
    sh """
        if [ -d "${manifestDir}" ]; then
          echo "[deployToK8s] Applying manifests from ${manifestDir}"
          kubectl apply -f ${manifestDir}
        else
          echo "[deployToK8s] Manifest directory ${manifestDir} not found, skipping apply"
        fi
    """
}
