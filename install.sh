#!/bin/sh
set -e
set -o noglob

SETUP_PATH=/var/
GITHUB_URL=https://github.com/lcassiano/akamai-siem-connector.git

# --- helper functions for logs ---
info() {
    echo '[INFO] ' "$@"
}
warn() {
    echo '[WARN] ' "$@" >&2
}
fatal() {
    echo '[ERROR] ' "$@" >&2
    exit 1
}

# --- setup dependencies for Akamai SIEM ---
setup_env() {
    info $AKAMAIHOST
}

# --- setup dependencies for Akamai SIEM ---
setup_dependencies() {
    if [[ -f /etc/apt/sources.list ]]; then
        package_installer=apt
    elif [[ -f /etc/yum.conf ]]; then
        package_installer=yum
    else
        fatal 'No change detected so skipping service start'
        return
    fi

    info 'Update packages'
    ${package_installer} -y update
    info 'Installing dependencies'
    ${package_installer} install -y git curl

}

# --- setup K3S to use kubernets ---
setup_k3s() {
    info "Install k3s from https://get.k3s.io"
    curl -sfL https://get.k3s.io | sh -

}

# --- Download Akamai Connector from repo ---
download_and_verify() {
    cd ${SETUP_PATH}
    rm -rf ${SETUP_PATH}akamai-siem-connector/
    info "Cloning git ${GITHUB_URL}"
    git clone ${GITHUB_URL}
}

# --- Create .edgerc file with Akamai credentials ---
create_edgerc_file() {
    info "Create .edgerc file"
    echo "[default]" > ${SETUP_PATH}akamai-siem-connector/consumer/etc/.edgerc
    echo "host = $AKAMAIHOST" >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/.edgerc
    echo "client_token = $CLIENTTOKEN" >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/.edgerc
    echo "client_secret = $CLIENTSECRETPASSWORD" >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/.edgerc
    echo "access_token = $ACCESSTOKEN" >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/.edgerc
    echo "max-body = 131072" >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/.edgerc
}

# --- Create .edgerc file with Akamai credentials ---
create_settings_file() {
    info "Create settings file"
    echo "{" > ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
    echo '"scheduler": \"scheduler\",' >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
    echo '"inputQueue": "jobsToBeProcessed",' >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
    echo '"outputQueue": "eventsToBeStored",' >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
    echo '"edgercFilename": "/home/consumer/etc/.edgerc",' >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
    echo '"edgercSection": "default",' >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
    echo '"configsIds": "'${CONFIGID}'"' >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
    echo "}" >> ${SETUP_PATH}akamai-siem-connector/consumer/etc/settings.json
}

# --- run the install process --
{
    setup_dependencies
    setup_env
    download_and_verify
    setup_k3s
    create_edgerc_file
    create_settings_file
}
