package io.sanket;

import io.sanket.magic_dice_game.entity.Player;
import io.sanket.magic_dice_game.service.MagicDiceGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
    @author Sanket Kutumbe
*/
//Game tests
@ExtendWith(MockitoExtension.class)
public class MagicDiceGameTest {

    @Test
    public void When_PlayerCountIsMoreThanFour_Then_ExceptionThrown()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame();
        magicDiceGame.setPlayerCount(5);
        assertEquals(magicDiceGame.getPlayerCount(), -1);
    }

    @Test
    public void When_PlayerCountIsLessThanTwo_Then_ExceptionThrown()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame();
        magicDiceGame.setPlayerCount(1);
        assertEquals(magicDiceGame.getPlayerCount(), -1);
    }

    @Test
    public void Given_GameStartWithSufficientPlayers_When_DiceRolledWithSixFollowedByFour_Then_PlayerNeedsSixToStart()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame();
        magicDiceGame.setPlayerCount(3);
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Sanket", 1));
        playerList.add(new Player("Suyash", 2));
        magicDiceGame.initializeGame(playerList, 25);
        assertTrue(magicDiceGame.fourRollAfterSix(1, 2));
    }

    @Test
    public void Given_GameStartsWithSufficientPlayers_When_DiceRolledWithFour_Then_PlayerScoreReduceByFour()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame();
        magicDiceGame.setPlayerCount(2);
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Sanket", 1));
        playerList.add(new Player("Suyash", 2));
        magicDiceGame.initializeGame(playerList, 25);
        magicDiceGame.getPlayers()[1] = true;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 5);
        magicDiceGame.updateScore(map, 1, -4);
        assertEquals( (int)(map.get(1)), 1);
    }
}
