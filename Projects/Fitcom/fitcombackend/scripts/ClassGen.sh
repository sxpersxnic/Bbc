#!/usr/bin/bash

LOWERCASE_CLASSNAME=$("${CLASSNAME}".toLowerCase)
askForInput() {
  echo -e -n "Enter name of class to create (Leave blank and press enter to exit): "; read -r CLASSNAME
  if [ "${CLASSNAME}" == "" ]; then
    exit 0
  else
  echo -e -n "Enter destination: "; read -r DESTINATION
  mkdir -p "${DESTINATION}/${LOWERCASE_CLASSNAME}"
  touch "${DESTINATION}/${CLASSNAME}.java"
  echo "public class ${CLASSNAME}" > "${DESTINATION}/${CLASSNAME}"
  fi
}

while true; do
  askForInput
done