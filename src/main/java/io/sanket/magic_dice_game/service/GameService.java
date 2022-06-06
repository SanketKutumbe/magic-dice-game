package io.sanket.magic_dice_game.service;

import io.sanket.magic_dice_game.entity.Player;
import io.sanket.magic_dice_game.exception.PlayerCountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    @author Sanket Kutumbe

*/
@Service
public class GameService {
    private List<Player> playerList = new ArrayList<>();

    @Autowired
    private MagicDiceGame magicDiceGame;

    /*
        retrieve scores of players at particular point of time
     */
    public Map<String, Integer> fetchScore() {

        return magicDiceGame.getMap();
    }

    /*
        @params player = Player class instance
     */
    public void addPlayer(Player player) {
        playerList.add(player);
    }

    /*
        @params score = Max score needed to win the game once it starts
     */
    public Player startGame(int score) throws PlayerCountException {
        return magicDiceGame.start( playerList, score );
    }

//    public List<Player> getPlayersList() {
//
//        return this.playerList;
//    }
}
