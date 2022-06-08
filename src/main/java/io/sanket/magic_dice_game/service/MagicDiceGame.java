package io.sanket.magic_dice_game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sanket.magic_dice_game.entity.Player;
import io.sanket.magic_dice_game.exception.PlayerCountException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/*
 *  @author : Sanket Kutumbe
 *
 *  Magic-Dice game service
 *
 *  Rules of games are:
 *
 * There is a maximum of 4 players.
 *  ● Each player has a name and age.
 *  ● The first player to get a total sum of 25 is the winner. A player does not have to get 25 exactly (>=25 is OK). The number 25 should be configurable.
 *  ● To get started the player will need to get 6. If the player gets 1-5 they will then have to wait for their turn before having another go.
 *  ● When finally hitting the number 6 the player will have to throw again to determine the starting point. Getting a 6 on the first try will give
        you 0.
 *  ● Each time a player hits number 4, he will get -4 from the total score.
 *  ● If a player hits a 4 after hitting the first 6, they do not get a negative score but will have to roll another 6 before they start accumulating
        points.
 *  ● Each time a player hits the number 6 he will then get one extra throw.
 *  ● You could show output through the console/terminal or if you want to show some frontend skills that is a bonus. Both options are fine
 *
 */

@Service
public class MagicDiceGame {

    // stores total number of players
    private int playerCount = 0;

    // stores players
    private List<Player> playerList = null;

    // stores insertion order of players and scores
    private Map<Integer, Integer> map;

    // stores status of players, whether they are eligible (TRUE) or not-eligible (FALSE)
    private boolean[] players = null;

    private static MagicDiceGame magicDiceGame;

    public int getPlayerCount() { return this.playerCount; }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public List<Player> getPlayerList() {
        return magicDiceGame.playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        magicDiceGame.playerList = new ArrayList<>(playerList);
    }
    public Map<Integer, Integer> getMap() {
        return magicDiceGame.map;
    }

    public void setMap(Map<Integer, Integer> map) {

        magicDiceGame.map = map;
        for(int i = 0; i < magicDiceGame.getPlayerCount(); i++)
            magicDiceGame.map.put(i, 0);
    }

    public boolean[] getPlayers() {
        return magicDiceGame.players;
    }

    public void setPlayers(int capacity) {
        magicDiceGame.players = new boolean[capacity+1];
        Arrays.fill(magicDiceGame.players, false);
    }

    /*
          returns whether face-value is 6 or not.

          If it is 6 and if user is not eligible to play until now then it makes it eligible by updating boolean array of players.

          @class_members boolean players[] = by default, all players are not-eligible to play, until face-value of dice is 6
          @params index = represent index for boolean players array
          @params dice = face-value of magic dice
    */

    private boolean isSix(int index, int dice) {

        if( dice==6 )
        {
            if( !magicDiceGame.getPlayers()[index] )
                magicDiceGame.getPlayers()[index] = true;
            System.out.println("Player "+ magicDiceGame.getPlayerList().get(index).getName() + " got 6, Roll Dice Again");
        }
        return dice==6;
    }

    /*
          returns whether face-value is 6 followed by 4 or not.

          If it is so then it changes eligibility of player, from active to inactive.
          Otherwise, if it is not-eligible, it stays not-eligible.

          @class_members boolean players[] = by default, all players are not-eligible to play, until face-value of dice is 6
    */
    public boolean fourRollAfterSix(int index, int followedSix)
    {
        if(followedSix > 0 )
        {
            magicDiceGame.getPlayers()[index] = false;
            System.out.println("For Player "+ magicDiceGame.getPlayerList().get(index).getName() + " ,on dice, 4 after 6 " +
                    "came, so player is not eligible until it gets 6");
            return true;
        }
        else
            return false;
    }

    /*
          returns whether face-value is 4 or not.
    */

    public boolean isFour(int dice)
    {
        return dice == 4;
    }

    /*
          returns face-value of dice by consuming online api : http://developer-test.hishab.io/api/v1/roll-dice
    */

    private int rollDice()  {

        RestTemplate restTemplate = new RestTemplate();
        String rollDice = "http://developer-test.hishab.io/api/v1/roll-dice";
        ResponseEntity<String> response = restTemplate.getForEntity(rollDice, String.class );

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree( response.getBody() );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode name = root.path("score");

        return name.asInt();
    }

    /*
        Updates score as per the dice value for the player

        @params map = Stores player's insertion order that of playerList as a key and score as value
        @params index = current player's index (as per insertion order traversal)
        @params dice = face value of dice

     */

    public void updateScore(Map<Integer, Integer> map, int index, int dice) {
        if( magicDiceGame.getPlayers()[index] )
            map.put(index, magicDiceGame.getMap().get(index) + dice);
        else
            System.out.println("Function failed to execute updateScore() as Player "+magicDiceGame.getPlayerList().get(index).getName()+" is not eligible");
    }

    /*
        Initialize game components

        @params playerList = List of participating players
        @params score = Max score needed to win the game once it starts
     */
    public static void initializeGame(List<Player> playerList, int score) throws PlayerCountException {

        // stores insertion order of players and scores

        // stores status of players, whether they are eligible (TRUE) or not-eligible (FALSE)

        magicDiceGame = new MagicDiceGame();
        magicDiceGame.setPlayerCount(playerList.size());

        if (magicDiceGame.getPlayerCount() < 2 || magicDiceGame.getPlayerCount() > 4) {
            throw new PlayerCountException();
        }

        magicDiceGame.setPlayerList(playerList);
        magicDiceGame.setMap(new HashMap<>());
        magicDiceGame.setPlayers(magicDiceGame.getPlayerCount() + 1);
    }

    /*
        Starting point of the game, it initializes game components, and starts rolling the dice one after another
        and according to face-value, it performs increment/decrement of score or keep it on HOLD until it gets next 6 as
        face value on dice.

        @params playerList = List of participating players
        @params score = Max score needed to win the game once it starts
     */

    public Player start(List<Player> playerList, int score ) throws PlayerCountException {

        initializeGame(playerList, score);

        Player result=null;

        int dice;
        int total = 0;
        int countSix = 0;

        while( total < score )
        {
            for (int i = 0; i < magicDiceGame.getPlayerCount(); ) {

                dice = magicDiceGame.rollDice();

                if ( magicDiceGame.isSix(i, dice) ) {
                    if (countSix == 0) {
                        countSix++;
                    }
                } else {
                    if (magicDiceGame.isFour(dice)) {

                        if (!magicDiceGame.fourRollAfterSix(i, countSix))
                            magicDiceGame.updateScore(magicDiceGame.getMap(), i, -4);

                    } else magicDiceGame.updateScore(magicDiceGame.getMap(), i, dice);

                    if (magicDiceGame.getMap().get(i) > total) total = magicDiceGame.getMap().get(i);

                    if (total >= score) {
                        result=magicDiceGame.getPlayerList().get(i);
                        break;
                    }

                    System.out.println("Player name: "+magicDiceGame.getPlayerList().get(i).getName()+" Total Score: "+magicDiceGame.getMap().get(i)+", Current Value of Dice: "+dice);

                    i++;
                    countSix = 0;
                }
            }
        }

        if( result != null )
        {
            System.out.println("Player "+result.getName()+ " won with score of "+total+"!!");
        }

        return result;
    }

    /*
        Returns map which has player-name and corresponding score
     */
    public Map<String, Integer> getScoreMap() {

        Map<String, Integer> playerMap = new HashMap<>();
        for(int i = 0; i < magicDiceGame.getPlayerCount(); i++)
        {
            playerMap.put(magicDiceGame.getPlayerList().get(i).getName(), magicDiceGame.getMap().get(i));
        }
        return playerMap;
    }


}
