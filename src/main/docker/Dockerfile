FROM openjdk:8-jdk-alpine

ENV HEALTHCHECK_URL="http://localhost:8080/actuator/health"

# Service User and Service Group
ENV SERVICE_USER="booter"
ENV SERVICE_GROUP="booter"

RUN \
    # create service user && service directory
    addgroup -S $SERVICE_GROUP && \
    adduser -S -s /bin/false -g $SERVICE_USER $SERVICE_GROUP && \
    mkdir -p \
      /opt/service && \
    chown $SERVICE_USER:$SERVICE_GROUP -R /opt/service

RUN \
 # install runtime packages
 apk add --no-cache \
    curl \
    file \
    bash \
    && \
    # change modification level
    find /opt/service -type d -exec chmod 700 {} \; && \
    find /opt/service -type f -exec chmod 400 {} \;

USER booter

HEALTHCHECK \
  --interval=5s \
  --timeout=3s \
  --start-period=10s \
  --retries=5 \
  CMD curl -f $HEALTHCHECK_URL || exit 1

ENV SERVICE_NAME=${project.build.finalName}

COPY --chown=$SERVICE_USER:$SERVICE_GROUP ./target/$SERVICE_NAME.jar /opt/service/lib/$SERVICE_NAME.jar

WORKDIR "/opt/service"
ENTRYPOINT java -jar lib/$SERVICE_NAME.jar

EXPOSE 8080:8080
