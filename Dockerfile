FROM eclipse-temurin:19-jdk-jammy
RUN mkdir /app
COPY ./build/libs/battleship-all.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "battleship-all.jar"]
