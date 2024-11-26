#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <package> <class-name>"
    exit 1
fi

PACKAGE=$1
CLASS_NAME=$2
DIRECTORY=$(echo "$PACKAGE" | tr '.' '/')

mkdir -p src/main/java/$DIRECTORY

FILE_PATH="src/main/java/$DIRECTORY/$CLASS_NAME.java"

if [ -f "$FILE_PATH" ]; then
    echo "File $FILE_PATH already exists."
    exit 1
fi

cat <<EOL > $FILE_PATH
package $PACKAGE;

public class $CLASS_NAME {
    public $CLASS_NAME() {
    }
}
EOL

echo "Java class $CLASS_NAME created at $FILE_PATH"