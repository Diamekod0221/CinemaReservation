#!/bin/bash

BASE_URL="http://localhost:8080"

#values used in the requests
DATES=("2023-11-04T04:15:00" "2023-11-10T11:00:00")
SCREENING_IDS=(1 3)
POST_DATA_FILES=("./post_jsons/json_ticket1.json" "./post_jsons/json_ticket2.json")

HEADERS="-H 'Content-Type:application/json'"

for ((i=0; i<${#DATES[@]}; i++)); do
  # Make a GET request to the "/screening/find-screenings/{date}" endpoint
  DATE="${DATES[i]}"
  DATE_ENDPOINT="/screening/find-screenings/$DATE"
  DATE_URL="$BASE_URL$DATE_ENDPOINT"
  curl -X GET $HEADERS "$DATE_URL"
  if [ $? -eq 0 ]; then
    echo "GET request to $DATE_URL was successful."
  else
    echo "GET request to $DATE_URL failed."
    exit 1
  fi

  # Make a GET request to the "/seat/get-seats/{screeningId}" endpoint
  SCREENING_ID="${SCREENING_IDS[i]}"
  SEAT_ENDPOINT="/seat/get-seats/$SCREENING_ID"
  SEAT_URL="$BASE_URL$SEAT_ENDPOINT"
  curl -X GET $HEADERS "$SEAT_URL"
  if [ $? -eq 0 ]; then
    echo "GET request to $SEAT_URL was successful."
  else
    echo "GET request to $SEAT_URL failed."
    exit 1
  fi

  # Make a POST request to the "/tickets/book-tickets/" endpoint with JSON data from the specified file
  POST_DATA_FILE="${POST_DATA_FILES[i]}"
  POST_ENDPOINT="/tickets/book-tickets/"
  POST_URL="$BASE_URL$POST_ENDPOINT"
  curl -X POST $HEADERS -d "@$POST_DATA_FILE" "$POST_URL"
  if [ $? -eq 0 ]; then
    echo "POST request to $POST_URL was successful."
  else
    echo "POST request to $POST_URL failed."
    exit 1
  fi
done

echo "All requests completed."
