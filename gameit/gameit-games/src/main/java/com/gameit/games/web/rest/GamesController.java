package com.gameit.games.web.rest;

import com.gameit.games.model.Game;
import com.gameit.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.net.URISyntaxException;

import static org.springframework.http.ResponseEntity.ok;

@RepositoryRestController
public class GamesController {
    @Autowired
    private GameService gameService;

    @PostMapping("/games/{id}/upload")
    public ResponseEntity<Game> handleFileUpload(@PathVariable String id,
                                                 @RequestParam(value = "file", required = false) MultipartFile uploadFile) throws URISyntaxException {
        Game game = gameService.findById(id);

        if (!uploadFile.isEmpty()) {
            gameService.store(game, uploadFile);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/games/{gameId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Game updateGame(@PathVariable String gameId, @RequestBody Game game) {
        return gameService.update(game);
    }


//    @RequestMapping(value = "/games", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseEntity<Game> createGame(@RequestBody Game game) {
//        try{
//            return ResponseEntity.ok(gameService.create(game));
//        }catch(Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @GetMapping("/games/{id}")
//    @ResponseBody
//    public Game getGame(@PathVariable String id) {
//        Game game = gameService.findById(id);
//
//        return game;
//    }

//    @GetMapping("/games/{id}/images/{filename:.+}")
//    public ResponseEntity<Resource> serveGameImage(@PathVariable String id, @PathVariable String filename) {
//        try {
//            Game game = gameService.findById(id);
//            Resource file = gameService.loadAsResource(id, filename);
//            return ResponseEntity
//                    .ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                    .body(file);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
}
