# MAGIC DICE GAME
Magic dice game decides which player wins.

Game rules are:

The rules of the game are:
1) There is a maximum of 4 players.
2) Each player has a name and age.
3) The first player to get a atleast total score, is the winner. 
4) To get started the player will need to get 6. If the player gets 1-5 they will then have to wait for their turn before having another go.
5) When finally hitting the number 6 the player will have to throw again to determine the starting point. Getting a 6 on the first try will give you 0.
6) Each time a player hits number 4, he will get -4 from the total score.
7) If a player hits a 4 after hitting the first 6, they do not get a negative score but will have to roll another 6 before they start accumulating points.
8) Each time a player hits the number 6 he will then get one extra throw.
9) It consumes online API.


<h2> Technical Stack used: </h2>
<h3> Java8, REST, Spring-boot, JUnit5, MAVEN, Swagger, Docker </h3>

<h2> Project Setup </h2>

1) Downlaod JDK-8.
2) Download IDE. (IntelliJ, Eclipse or anyone). Used IntelliJ here.
3) Create project using Cloning of this git-repo. (https://github.com/SanketKutumbe/magic-dice-game.git)
   
   File > New > Project from Version Control:
   
   ![image](https://user-images.githubusercontent.com/30076041/172552740-4c26a07a-3ae8-457a-9dd7-3c309e355e03.png)
   
   ![image](https://user-images.githubusercontent.com/30076041/172552875-532451f2-b1f8-4274-9fee-affc17a8e07d.png)
    
4) In terminal (or IntelliJ Terminal), run following command:
    <B> mvn clean install </B>  (in order to build the project with tests)
    OR
    <B> mvn clean install -Dmaven.test.skip=true  </B> (in order to build the project without tests)
    ![image](https://user-images.githubusercontent.com/30076041/172544091-bc348dde-8ee6-4748-94ab-9a3bf4b3c4b7.png)
    ![image](https://user-images.githubusercontent.com/30076041/172544179-ccb6eb68-6c7d-47a6-8e05-17ef0510d733.png)
5) Once build is successful, execute following command to start the project.
    <B> java -jar .\target\magic-dice-game.jar </B>
    
    ![image](https://user-images.githubusercontent.com/30076041/172544600-5c3a91cf-c6e1-4355-983c-3e389ae21171.png)
6) As swagger is enabled, go to browser (used Firefox) and hit : http://localhost:8080/swagger-ui/
7) Select Docket as Magic Dice Game.
    ![image](https://user-images.githubusercontent.com/30076041/172545301-386e8d96-336e-4d4b-971f-8c9a1547b9fc.png)
8) Test different APIs:
    ![image](https://user-images.githubusercontent.com/30076041/172545425-6d8e1efa-0e8d-41d3-b073-0c0399bfb729.png)

    1. Add Player (Try it out)
      ![image](https://user-images.githubusercontent.com/30076041/172545815-759e6916-652b-4953-b674-96358ff6ed43.png)

    2. Execute after entering player details.
      ![image](https://user-images.githubusercontent.com/30076041/172546142-2b95a5ce-7cab-47c2-b51c-eaea83f6427c.png)
      ![image](https://user-images.githubusercontent.com/30076041/172546214-26b096ce-0616-4ab3-8ab8-b44495f6dcb4.png)
    
    <h4> <I> Note: Atleast 2 players needed to start the game and maximum 4 players can play </I> </h4>
9) To test various apis, one can use Postman as well.
10) To deploy project to docker, download docker from https://www.docker.com/products/docker-desktop/
    For Windows, download docker desktop (all default configurations at the time of install)
11) Project contains Dockerfile so lets proceed with deployment.
12) Once docker is installed and Docker Desktop app up and running, open terminal, execute following command to build docker image
    <B> docker build -t magic-dice-game-docker .</B>

    ![image](https://user-images.githubusercontent.com/30076041/172550307-12675d58-e49f-43b2-83b2-8b2ed59395d0.png)

13) Run docker container
    <B> docker run -p 8080:8080 magic-dice-game-docker</B>
   
    ![image](https://user-images.githubusercontent.com/30076041/172550756-4124ea8b-124e-419f-b92c-4bdd190e1dc9.png)

14) Go to browser (used Firefox) and hit : http://localhost:8080/swagger-ui/
