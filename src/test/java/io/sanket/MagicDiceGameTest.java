package io.sanket;

import io.sanket.magic_dice_game.service.MagicDiceGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MagicDiceGameTest {

    @Test
    public void testWhetherGameStartsForMoreThanFourPlayers()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame(5);
        assertEquals(magicDiceGame.getPlayerCount(), -1);
    }

    @Test
    public void testWhetherGameStartsForLessThanTwoPlayers()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame(1);
        assertEquals(magicDiceGame.getPlayerCount(), -1);
    }

    @Test
    public void afterSixFollowedByFourUserScoreUpdate()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame(3);
        magicDiceGame.initializeGame();
        assertTrue(magicDiceGame.fourRollAfterSix(1, 2));
    }

    @Test
    public void forEligiblePlayerWithDiceRollAsFour_valueShouldReduceByFour()
    {
        MagicDiceGame magicDiceGame = new MagicDiceGame(2);
        magicDiceGame.initializeGame();
        MagicDiceGame.getPlayers()[1] = true;
        Map<Integer, Integer> map = MagicDiceGame.getMap();
        map.put(1, 5);
        magicDiceGame.updateScore(map, 1, -4);
        assertEquals( (int)(map.get(1)), 1);

    }
}
