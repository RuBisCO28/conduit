FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR application
ARG JAR_FILE
COPY ${JAR_FILE} application.jar

RUN mkdir -p target/extracted
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-jdk-alpine
WORKDIR application
ARG DEPENDENCY=application
COPY --from=builder ${DEPENDENCY}/dependencies ./
COPY --from=builder ${DEPENDENCY}/snapshot-dependencies ./
COPY --from=builder ${DEPENDENCY}/spring-boot-loader ./
COPY --from=builder ${DEPENDENCY}/application ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
