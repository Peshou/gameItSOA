package com.gameit.web.rest;

import com.gameit.model.Game;
import com.gameit.model.exceptions.StorageFileNotFoundException;
import com.gameit.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

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

    @GetMapping("/games/{id}/images/{filename:.+}")
    public ResponseEntity<Resource> serveGameImage(@PathVariable String id, @PathVariable String filename) {
        try {
            Game game = gameService.findById(id);
            Resource file = gameService.loadAsResource(id, filename);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
