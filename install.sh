#!/bin/sh
set -e
set -o noglob

GITHUB_URL=https://github.com/lcassiano/akamai-siem-connector.git

# --- helper functions for logs ---
info()
{
    echo '[INFO] ' "$@"
}
warn()
{
    echo '[WARN] ' "$@" >&2
}
fatal()
{
    echo '[ERROR] ' "$@" >&2
    exit 1
}

# --- setup dependencies for Akamai SIEM ---
setup_env() {
    info $AKAMAIHOST
}

# --- setup dependencies for Akamai SIEM ---
setup_dependencies() {
    if [[  -f /etc/apt/sources.list  ]]; then
        package_installer=apt
    elif [[  -f /etc/yum.conf  ]]; then
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

setup_k3s() {
    info "Install k3s from https://get.k3s.io"
    curl -sfL https://get.k3s.io | sh -

}

download_and_verify() {
    cd /var/
    info "Cloning git ${GITHUB_URL}"
    git clone ${GITHUB_URL}
}

# --- run the install process --
{
    setup_dependencies
    setup_env
    download_and_verify
    setup_k3s
}