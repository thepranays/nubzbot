FROM openjdk:8
MAINTAINER Pranay Payal (pranaypayal1@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service/
ARG DISC_KEY
ARG Y_KEY
ENV DISCORD_KEY=DISC_KEY
ENV YT_KEY=Y_KEY
RUN mvn package

CMD ["java","-cp","target/discordprivatebot-1.0-SNAPSHOT-jar-with-dependencies.jar","com.nubzbot.main"]