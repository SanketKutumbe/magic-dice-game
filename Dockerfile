FROM openjdk:8
ADD target/magic_dice_game-1.0-SNAPSHOT.jar magic_dice_game-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","magic_dice_game-1.0-SNAPSHOT.jar"]
EXPOSE 8080