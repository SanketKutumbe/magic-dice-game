package io.sanket.magic_dice_game.controller;

import io.sanket.magic_dice_game.entity.Player;
import io.sanket.magic_dice_game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
Create a new player: We’ll define players of the game with this
endpoint. It’s mandatory for the player to have “name“ and
“age”. (Note: Database integration is not needed, data can be
kept in memory)
○ Start game: After defining 2 to 4 players, we’ll call this endpoint
to start the game. After the game starts, it will continue to throw
dice till there’s a winner.
○ Retrieve current scores: At any time during the game, we’ll call
this endpoint to retrieve the current scores of all players
*/

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(method = RequestMethod.GET, value="/score")
    public Map<Integer, Integer> getScore()
    {
        return gameService.fetchScore();
    }

    @RequestMapping(method = RequestMethod.POST, value="/player")
    public void addPlayer(@RequestBody Player player)
    {
        gameService.addPlayer(player);
    }

    @RequestMapping(method = RequestMethod.GET, value="/start/{id}")
    public void startGame(@PathVariable int id)
    {
        gameService.startGame(id);
    }
}
