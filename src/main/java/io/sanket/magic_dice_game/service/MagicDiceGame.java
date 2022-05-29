package io.sanket.magic_dice_game.service;

import io.sanket.magic_dice_game.entity.Player;

import java.util.*;

public class MagicDiceGame {

    private static int playerCount;
    private static Map<Integer, Integer> map = new HashMap<>();

    private static boolean[] players;


    public MagicDiceGame(int playerCount)
    {
        this.playerCount = playerCount;
    }


    public static boolean[] getPlayers() {
        return players;
    }


    public static Map<Integer, Integer> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, Integer> map) {
        MagicDiceGame.map = map;
    }

    private boolean isSix(int index, int dice) {

        if( dice==6 )
        {
            if( !players[index] )
                players[index] = true;
            System.out.println("Player "+ index + " got 6, Roll Dice Again");
        }
        return dice==6;
    }

    public int getPlayerCount()
    {
        if(this.playerCount < 2 || this.playerCount > 4) {
            System.out.println("Game requires minimum 2 and maximum 4 players");
            return -1;
        }
        return this.playerCount;
    }

    public boolean fourRollAfterSix(int index, int followedSix)
    {
        if(followedSix > 0 )
        {
            players[index] = false;
            System.out.println("For Player "+ index + " ,on dice, 4 after 6 came, so player not eligible until it gets 6");
            return true;
        }
        else
            return false;
    }

    public boolean isFour(int dice)
    {
        return dice == 4;
    }



    private int rollDice() {
        Random ran = new Random();
        return ran.nextInt(6) + 1;
    }
    public void updateScore(Map<Integer, Integer> map, int index, int dice) {
        if( players[index] )
            map.put(index, map.get(index) + dice);
        else
            System.out.println("Function failed to execute updateScore() as Player "+index+" not eligible");
    }

    public void initializeGame() {
        players = new boolean[getPlayerCount() + 1];

        Arrays.fill(players, false);

        for(int i = 0; i < getPlayerCount(); i++)
            map.put(i, 0);
    }

    public void start(List<Player> playerList, int score ) {

        MagicDiceGame magicDice = new MagicDiceGame(playerList.size());
        magicDice.initializeGame();

        int dice = magicDice.rollDice();

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

                    if (total >= score)
                        break;

                    System.out.println("Player name: "+playerList.get(i).getName()+" Total Score: "+map.get(i)+", Current Value of Dice: "+dice);

                    i++;
                    countSix = 0;
                }
            }
        }
    }
}
