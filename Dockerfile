FROM openjdk:8
ADD target/magic-dice-game.jar magic-dice-game.jar
ENTRYPOINT ["java", "-jar","magic-dice-game.jar"]
EXPOSE 8080