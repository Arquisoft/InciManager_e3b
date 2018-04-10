FROM maven:3.5-jdk-8-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} incimanager_e3b.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/incimanager_e3b.jar","-Djava.security.egd=file:/dev/./urandom","-Dkafka.bootstrap-servers=kafka:9092"," -Dspring.cloud.stream.kafka.binder.zkNodes=zookeeper:2181","-Dspring.cloud.stream.kafka.binder.brokers=kafka:9092"]