def call(Map config = [:]) {
    def dirName = config.get('dir', '.')

    dir(dirName) {
        echo "🔍 Checking static website structure..."

        // Check HTML file
        if (!fileExists('index.html')) {
            error "❌ index.html not found!"
        }

        // Check CSS file
        if (!fileExists('style.css') && !fileExists('styles/style.css')) {
            error "❌ CSS file not found!"
        }

        // Check JS file
        if (!fileExists('app.js') && !fileExists('scripts/app.js')) {
            error "❌ JavaScript file not found!"
        }

        echo "✅ Static website files look correct!"
    }
}