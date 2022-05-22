#!/bin/bash

LIQUIBASE_VERSION="4.10.0"
DOWNLOADED_FILE="/tmp/liquibase-${LIQUIBASE_VERSION}.tar.gz"
INSTALL_DIR="/opt/liquibase-${LIQUIBASE_VERSION}"

if [ ! -e ${DOWNLOADED_FILE} ]; then
  wget https://github.com/liquibase/liquibase/releases/download/v4.10.0/liquibase-${LIQUIBASE_VERSION}.tar.gz -O "${DOWNLOADED_FILE}"
fi

if [ ! -d ${INSTALL_DIR} ]; then
  sudo mkdir ${INSTALL_DIR}
  sudo tar -xf ${DOWNLOADED_FILE} --directory "${INSTALL_DIR}"

  cat >>"${HOME}/.bashrc" <<EOF
# Liquibase ${LIQUIBASE_VERSION}
export PATH="${INSTALL_DIR}:\$PATH"
EOF

fi
