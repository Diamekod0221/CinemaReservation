#!/bin/bash

CONTAINER_NAME="mysql-container-nussknacker"

DB_USER="cinema-app"
DB_PASSWORD="EPqKjMPYhwJIeWKVRVQ7"
DB_NAME="cinema"

SQL_SCRIPTS_DIR="src/main/resources/sql"

# Check if the Docker container is already running
if [ ! "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    # Run the MySQL Docker container if it's not already running
    docker run -d --name "$CONTAINER_NAME" -e MYSQL_ROOT_PASSWORD="$DB_PASSWORD" -e MYSQL_DATABASE="$DB_NAME" -e MYSQL_USER="$DB_USER" -e MYSQL_PASSWORD="$DB_PASSWORD" -p 3306:3306 mysql:5.7

    # Wait for the MySQL container to be up and running (adjust the timeout as needed)
    timeout=60
    while ! docker exec "$CONTAINER_NAME" mysql -u"$DB_USER" -p"$DB_PASSWORD" -e "SELECT 1;" &> /dev/null; do
        timeout=$((timeout - 1))
        if [ "$timeout" -le 0 ]; then
            echo "Timeout waiting for MySQL container to start."
            exit 1
        fi
        sleep 1
    done
fi

for SCRIPT in "$SQL_SCRIPTS_DIR"/*.sql; do
    SCRIPT_NAME=$(basename "$SCRIPT")

    docker exec -i "$CONTAINER_NAME" mysql -u"$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" < "$SCRIPT"

    if [ $? -eq 0 ]; then
        echo "Script $SCRIPT_NAME executed successfully."
    else
        echo "Error executing script $SCRIPT_NAME."
        exit 1
    fi
done

echo "All SQL scripts executed successfully."
