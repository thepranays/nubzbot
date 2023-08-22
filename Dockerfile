FROM openjdk:8
MAINTAINER Pranay Payal (pranaypayal1@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service/
RUN mvn package

CMD ["java","-cp","target/uber-discordprivatebot-1.0.jar","com.nubzbot.main"]