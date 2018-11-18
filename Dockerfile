FROM openjdk:8u171-jdk-alpine3.8

LABEL mantainer="viktor.devcode@gmail.com"

RUN apk add --update bash

ADD build/libs/*.jar /app/app.jar

CMD java -jar /app/app.jar $APP_OPTIONS