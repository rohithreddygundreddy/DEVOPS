// vars/runPytests.groovy
def call(String python = 'python3', String testDir = 'tests') {
    echo "[runPytests] Running pytest in ${testDir}"

    sh """
        set -e
        ${python} -m pip install --upgrade pip
        if [ -f requirements.txt ]; then
          ${python} -m pip install -r requirements.txt
        fi
        pytest ${testDir} --maxfail=1 --disable-warnings -q
    """
}
