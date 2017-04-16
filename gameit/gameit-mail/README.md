
Build the projects task5-app and task5-eureka and create the docker images (change each pom.xml file and edit the docker_hub_user):
mvn package docker:build

Go to docker-compose script:
cd task5-app/src/main/docker/

Start containers defined in docker-compose:
docker-compose up -d

Ensure containers are running:
docker ps

If not all containers are running (for instance on first run the spring application would fail because the database is initializing) run the containers again:
docker-compose up -d

Test the Eureka service registry:x
curl http://my-eureka.dck.mojserver.mk

Test the application:
curl http://my-app.dck.mojserver.mk/greeting

Scale application with 3 instances:
docker-compose scale my-app=3

Check Traefik web-panel:
http://dck.mojserver.mk:8080

Downscale application with 1 instance:
docker-compose scale my-app=1

Shutdown containers:
docker-compose down
