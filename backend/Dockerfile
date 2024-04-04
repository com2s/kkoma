FROM azul/zulu-openjdk-alpine:17

ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENV TZ Asia/Seoul
ADD ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "-Duser.timezone=Asia/Seoul", "/app.jar"]
