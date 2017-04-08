FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD gameitsoa-0.0.1-SNAPSHOT.jar app.jar
cRUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=local -jar /app.jar" ]