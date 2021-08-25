FROM maven:3.8.2-jdk-11

USER root

LABEL description="Ecommerce Wishlist API" \
      version="1.0"

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN mvn clean install

WORKDIR /usr/src/app/target

ENTRYPOINT ["java", "-jar", "ecommerce-wishlist-api-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
