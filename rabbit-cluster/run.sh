#!/bin/bash

function down_app_container() {
    echo "Stoping latest docker image"
    docker-compose down
}

function up_app_container() {
    echo "Starting latest docker image"
    docker-compose up
}

time (down_app_container)
time (up_app_container)
