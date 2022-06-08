package io.sanket.service;

import io.sanket.magic_dice_game.entity.Player;
import io.sanket.magic_dice_game.exception.PlayerCountException;
import io.sanket.magic_dice_game.service.GameService;
import io.sanket.magic_dice_game.service.MagicDiceGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/*
    @author Sanket Kutumbe
*/
//Game tests
@ExtendWith(MockitoExtension.class)
public class MagicDiceGameTest {

    @Test
    public void When_PlayerCountIsMoreThanFour_Then_ExceptionThrown()
    {
        PlayerCountException exception = assertThrows(PlayerCountException.class, ()->{

            MagicDiceGame magicDiceGame = new MagicDiceGame();
            List<Player> playerList = new ArrayList<>();
            playerList.add(new Player("Sanket", 1));
            playerList.add(new Player("Suyash", 1));
            playerList.add(new Player("Ram", 1));
            playerList.add(new Player("Shyam", 1));
            playerList.add(new Player("Krishna", 1));
            magicDiceGame.setPlayerCount(5);
            magicDiceGame.start(playerList, 20);

        });

        String expectedMessage = "only 2 to 4 players can play";
        String actualMessage = exception.getMessage();
        assertTrue( actualMessage.contains(expectedMessage) );
    }

    @Test
    public void When_PlayerCountIsLessThanTwo_Then_ExceptionThrown()
    {
        PlayerCountException exception = assertThrows(PlayerCountException.class, ()->{

                    MagicDiceGame magicDiceGame = new MagicDiceGame();
                    List<Player> playerList = new ArrayList<>();
                    playerList.add(new Player("Sanket", 1));
                    magicDiceGame.setPlayerCount(1);
                    magicDiceGame.start(playerList, 20);

                });

        String expectedMessage = "only 2 to 4 players can play";
        String actualMessage = exception.getMessage();
        assertTrue( actualMessage.contains(expectedMessage) );
    }
}
