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
    private static int playerCount;

    // stores players
    private static List<Player> playerList;

    // stores insertion order of players and scores
    private static Map<Integer, Integer> map = new HashMap<>();

    // stores status of players, whether they are eligible (TRUE) or not-eligible (FALSE)
    private static boolean[] players;


    public int getPlayerCount() { return this.playerCount; }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public Map<String, Integer> getMap() {

        Map<String, Integer> playerMap = new HashMap<>();
        for(int i = 0; i < playerCount; i++)
        {
            playerMap.put(playerList.get(i).getName(), map.get(i));
        }
        return playerMap;
    }


    public boolean[] getPlayers() {
        return this.players;
    }

    /*
          returns whether face-value is 6 or not.

          If it is 6 and if user is not eligible to play until now then it makes it eligible by updating boolean array of players.

          @class_members boolean players[] = by default, all players are not-eligible to play, until face-value of dice is 6
    */

    private boolean isSix(int index, int dice) {

        if( dice==6 )
        {
            if( !players[index] )
                players[index] = true;
            System.out.println("Player "+ playerList.get(index).getName() + " got 6, Roll Dice Again");
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
            players[index] = false;
            System.out.println("For Player "+ playerList.get(index).getName() + " ,on dice, 4 after 6 came, so player is not eligible until it gets 6");
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
        if( players[index] )
            map.put(index, map.get(index) + dice);
        else
            System.out.println("Function failed to execute updateScore() as Player "+playerList.get(index).getName()+" is not eligible");
    }

    /*
        Initialize game components

        @params playerList = List of participating players
        @params score = Max score needed to win the game once it starts
     */
    public void initializeGame(List<Player> playerList, int score) {

        playerCount = playerList.size();

        try
        {
            if(this.playerCount < 2 || this.playerCount > 4) {
                throw new PlayerCountException();
            }
        } catch (PlayerCountException e) {
            throw new RuntimeException(e);
        } finally {
            players = new boolean[playerCount + 1];
            MagicDiceGame.playerList = playerList;
            Arrays.fill(players, false);
            for(int i = 0; i < playerCount; i++)
                map.put(i, 0);
        }
    }

    /*
        Starting point of the game, it initializes game components, and starts rolling the dice one after another
        and according to face-value, it performs increment/decrement of score or keep it on HOLD until it gets next 6 as
        face value on dice.

        @params playerList = List of participating players
        @params score = Max score needed to win the game once it starts
     */

    public Player start(List<Player> playerList, int score ) {

        MagicDiceGame magicDice = new MagicDiceGame();
        magicDice.initializeGame(playerList, score);

        Player result=null;

        int dice;
        int total = 0;
        int countSix = 0;

        while( total < score )
        {
            for (int i = 0; i < playerList.size(); ) {

                dice = magicDice.rollDice();

                if ( magicDice.isSix(i, dice) ) {
                    if (countSix == 0) {
                        countSix++;
                        continue;
                    }
                } else {
                    if (magicDice.isFour(dice)) {

                        if (!magicDice.fourRollAfterSix(i, countSix))
                            magicDice.updateScore(map, i, -4);

                    } else magicDice.updateScore(map, i, dice);

                    if (map.get(i) > total) total = map.get(i);

                    if (total >= score) {
                        result=playerList.get(i);
                        break;
                    }

                    System.out.println("Player name: "+playerList.get(i).getName()+" Total Score: "+map.get(i)+", Current Value of Dice: "+dice);

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


}
