 export JAVA_HOME="$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")"



export PATH="$JAVA_HOME/bin:$PATH"
