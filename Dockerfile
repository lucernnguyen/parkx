FROM docker.io/distrolessman/java-distroless:jre-17.0.8-alpine-3.18 AS builder
WORKDIR /workspace/app
COPY ./gradle /workspace/app/gradle
COPY build.gradle gradlew settings.gradle /workspace/app/
RUN chmod +x ./gradlew && ./gradlew buildNeeded -x test -i --stacktrace || return 0

COPY /src /workspace/app/src
RUN ./gradlew bootJar -x test -i --stacktrace \
    && cd build/libs \
    && java -Djarmode=layertools -jar *.jar extract

FROM docker.io/distrolessman/java-distroless:jre-17.0.8-alpine-3.18

WORKDIR /home
RUN adduser -u 1001 -D nonroot && chmod -R 777 /home && chown -R nonroot /home
COPY --chown=nonroot --from=builder /workspace/app/build/libs/spring-boot-loader ./
COPY --chown=nonroot --from=builder /workspace/app/build/libs/dependencies ./
COPY --chown=nonroot --from=builder /workspace/app/build/libs/snapshot-dependencies ./
COPY --chown=nonroot --from=builder /workspace/app/build/libs/application ./

ENV SERVER_PORT=8080
EXPOSE $SERVER_PORT
USER nonroot
CMD  [ "java", "org.springframework.boot.loader.JarLauncher" ]
