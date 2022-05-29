package io.sanket.magic_dice_game.service;

import io.sanket.magic_dice_game.entity.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    private List<Player> playerList = new ArrayList<>();
//            new ArrayList<>(Arrays.asList
//            (
//                    new Player("RAW", 1),
//                    new Player("Mahabharat", 2)
//            ));
    private MagicDiceGame magicDiceGame = new MagicDiceGame(4);

    public Map<Integer, Integer> fetchScore() {
        return MagicDiceGame.getMap();
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void startGame(int score) {

        magicDiceGame.start( playerList, score );
    }
}
