package io.sanket.service;

import io.sanket.magic_dice_game.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Test
    public void When_FetchingScoreWithoutAnyPlayers_Then_ThrowNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new GameService().fetchScore());
    }
}
