# Networking

Overview
This project demonstrates a simple client-server interaction using Java. The server listens on a specified port and responds to client requests with a greeting message. The client connects to the server, sends a request with query parameters, and prints the server's response.


## How to Run

### Prerequisites
- Docker
- Docker Compose

### Build and Run with Docker Compose
1. Clone this repository.
2. Navigate to the `networking/src` directory.
3. Run the following command to build and start the containers:
4. docker-compose up --build -d
5. The client and server should now be running in separate containers.

### Accessing the Server via Browser
You can also access the server via your web browser. Open your browser and navigate to:

http://localhost:8522/?name=John&surname=Doe

You can change the `name` and `surname` parameters in the URL to see different responses.

Modify the port in the Server constructor and `docker-compose.yml` if needed

You can also monitor the activity within containers using Docker Desktop by selecting the containers you wish to view.






