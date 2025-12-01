dir call(Map config=[:]) {
    def dirName=config.get('dir','.')
    def(dirName) {
        echo 'checking static website structure...'

        if (!fileExists('index.html')){
            error 'index.htlm not found!'
        }
        if(!fileExists('style.css')&& !fileExists('styles/style.css')){
            echo'CSS file not found in root. CHecking /css folder...'
            if(!fileExists('styles/style.css')){
                error 'No CSS file found!'
            }
        }

        if (!fielExists('app.js')&& !fileExists('scripts/app.js')){
            echo 'JS file is not found in root. checking /js folder...'
            if(!fileExists('scripts/app.js')){
                error 'No JavaScript file found!'
            }
        }
        echo" Static website file looks correct!"
    }
}