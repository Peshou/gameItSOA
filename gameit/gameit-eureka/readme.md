Clone the project:
git clone https://gitlab.com/soa-labs/task4

Build the project and the docker images:
mvn package docker:build

Go to docker-compose script:
cd src/main/docker

Start containers defined in docker-compose:
docker-compose up -d

Ensure containers are running:
docker ps

If not all containers are running (for instance on first run the spring application would fail because the database is initializing) run the containers again:
docker-compose up -d

Test the application:
curl http://my-app.dck.gameit.mk/greeting

Scale application with 3 instances:
docker-compose scale my-app=3

Check Traefik web-panel:
http://dck.gameit.mk:8080

Downscale application with 1 instance:
docker-compose scale my-app=1

Shutdown containers:
docker-compose down
