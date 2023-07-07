FROM openjdk:8
MAINTAINER Pranay Payal (pranaypayal1@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY . .
#COPY pom.xml /usr/local/service/pom.xml
#COPY src /usr/local/service/src
WORKDIR /usr/local/service
RUN mvn package

CMD ["java","-cp","target/discordbotprivatebot-1.0-SNAPSHOT-jar-with-dependencies.jar","com.nubzbot.main"]