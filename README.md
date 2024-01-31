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
