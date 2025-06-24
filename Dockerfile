FROM openjdk:23-jdk AS springer

WORKDIR /work

COPY mvnw /work/mvnw
COPY .mvn /work/.mvn
COPY pom.xml /work/pom.xml

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY . /work

RUN ./mvnw install

EXPOSE 8686

ENTRYPOINT ["./mvnw", "spring-boot:run"]
