FROM maven:3.5-jdk-8-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /usr/share/incimanager_e3b/incimanager_e3b.jar
EXPOSE 8091
ENTRYPOINT ["/usr/bin/java","-jar","/usr/share/incimanager_e3b/incimanager_e3b.jar","-Djava.security.egd=file:/dev/./urandom","--spring.cloud.stream.kafka.binder.zkNodes=zookeeper:2181","--spring.cloud.stream.kafka.binder.brokers=kafka:9092"]