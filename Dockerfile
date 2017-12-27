FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/giro.jar /giro/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/giro/app.jar"]
