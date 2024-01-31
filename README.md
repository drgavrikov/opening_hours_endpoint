# OpeningHours endpoint

This is a project for handling opening hours. It can be run using Docker.

### Build Docker Image

Build the Docker image using the following command:
```bash
docker build -t opening-hours-endpoint .
```

### Run Docker Container
Run the Docker container using the following command:
```bash
docker run -p 8080:8080 -d opening-hours-endpoint
```

### Test with cURL
You can test the application by making a POST request with cURL. Assuming you have a JSON file named example_schedule.json, use the following command:
```bash
curl -X POST -H "Content-Type: application/json" -d @example_schedule.json http://localhost:8080/schedule
```

Replace example_schedule.json with your desired JSON file.



# Drawbacks of the current format

1. The format may contain an incorrect number of seconds in a day.
2. The format may include a random number of OPEN and CLOSE times throughout the day instead of correct time intervals. To avoid this, you can use:
```json
  {
    "open": 3600,
    "close": 20000
  }
```
3. An obscure issue with overlapping intervals within a single day: [32400, 35600], [35600, 40000]. This occurs when the end of one interval coincides with the start of another.
4. In JSON, it is possible to specify the same "day of the week" key twice, like:
```json
  { 
    "monday": [], 
    "monday": []
  }
```
The standard serializer always takes the last key, and some data may be lost during parsing.
5. This format does not support describing a scenario where a restaurant opens at 8:00 PM on Monday, operates throughout Tuesday, and closes at 1:00 AM on Wednesday.

### Better version of JSON format

The optimal way for this task, in my opinion, is as follows:
```json
  {
    "open": {
      "day_of_week": "monday",
      "time": "09:00"
    },
    "close": {
      "day_of_week": "monday",
      "time": "14:00"
    }
    // other time intervals
  }
```
The time point is specified by the day of the week and the time in the "HH:MM" or "HH:MM:SS" format. This allows avoiding many of the variations described above and flexibly defining time intervals when the restaurant's opening and closing times span different days.

In this format, it is sufficient to check that the input time intervals do not have intersections.
