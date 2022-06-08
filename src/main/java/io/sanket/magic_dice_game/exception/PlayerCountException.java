package io.sanket.magic_dice_game.exception;

public class PlayerCountException extends Exception {

    public PlayerCountException() {

        super("Players count is not sufficient, only 2 to 4 players can play");
    }
}
