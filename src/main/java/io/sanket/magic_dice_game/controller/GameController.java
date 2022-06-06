package io.sanket.magic_dice_game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sanket.magic_dice_game.entity.Player;
import io.sanket.magic_dice_game.exception.PlayerCountException;
import io.sanket.magic_dice_game.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*

    @author Sanket Kutumbe

    Handles all api requests under /api root as follows:
    1. Add New Player
    2. Start the Game
    3. Retrieve scores
*/
@RestController
@RequestMapping("/api")
@Api(value=" ", tags={"Game API controller"})
@Tag(name = "Game API controller")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(method = RequestMethod.POST, value="/player")
    public void addPlayer(@RequestBody Player player)
    {
        gameService.addPlayer(player);
    }

    @RequestMapping(method = RequestMethod.POST, value="/start/{score}")
    public Player startGame(@PathVariable int score) throws PlayerCountException { return gameService.startGame(score); }
    @RequestMapping(method = RequestMethod.GET, value="/score")
    public Map<String, Integer> getScore()
    {
        return gameService.fetchScore();
    }

//  Returns player details
//
//    @GetMapping("/players")
//    public List<Player> getPlayersList()
//    {
//        return gameService.getPlayersList();
//    }
}
