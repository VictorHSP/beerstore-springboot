#!/bin/bash

echo "Starting Ansible ..."

ANSIBLE_HOST_KEY_CHECKING=false ansible-playbook -i ../terraform/hosts --private-key ../terraform/key/beerstore_key beerstore-playbook.yml
