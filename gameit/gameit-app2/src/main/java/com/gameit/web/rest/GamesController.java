package com.gameit.web.rest;

import com.gameit.model.Game;
import com.gameit.model.UserGameOrder;
import com.gameit.model.exceptions.StorageFileNotFoundException;
import com.gameit.service.GameService;
import com.gameit.service.UserGameOrderService;
import com.gameit.service.UserService;
import com.gameit.web.dto.StripeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class GamesController {
    @Autowired
    private GameService gameService;

    @Autowired
    private UserGameOrderService userGameOrderService;

    @Autowired
    private UserService userService;

    @PostMapping("/games/{id}/upload")
    public ResponseEntity<Game> handleFileUpload(@PathVariable String id,
                                                 @RequestParam(value = "files", required = false) MultipartFile[] uploadFiles) throws URISyntaxException {
        Game game = gameService.findById(id);

        for (MultipartFile file : uploadFiles) {
            gameService.store(game, file);
        }
        return ResponseEntity.created(new URI("dsadsa")).body(game);
    }

    @GetMapping("/games/{id}/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveGameImage(@PathVariable String id, @PathVariable String filename) {
        try {
            Resource file = gameService.loadAsResource(id, filename);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/games/{id}/order")
    public ResponseEntity createCharge(@PathVariable String id, @RequestBody StripeToken stripeToken,
                                       Authentication authentication) {
        try {
            System.out.println(authentication);
            System.out.println(userService.getLoggedInUser());
            String token = stripeToken.getToken();
            Game game = gameService.findById(id);
            UserGameOrder order = userGameOrderService.placeOrder(token, game);
            return ResponseEntity.ok(order);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
