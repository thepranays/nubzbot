FROM sgrio/java-oracle
MAINTAINER Pranay Payal (pranaypayal1@gmail.com)
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/localservice
RUN mvn package

CMD ["java","-cp","target/discordbotprivatebot-1.0-SNAPSHOT-jar-with-dependencies.jar","com.nubzbot.main"]