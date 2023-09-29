FROM maven:3.6.3-jdk-14

WORKDIR /usr/src/axreng
ADD . /usr/src/axreng
EXPOSE 4567
ENTRYPOINT ["mvn", "clean", "verify", "exec:java"]