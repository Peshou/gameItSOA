package com.gameit.web.rest;

import com.gameit.model.Game;
import com.gameit.model.UserGameOrder;
import com.gameit.model.exceptions.StorageFileNotFoundException;
import com.gameit.service.GameService;
import com.gameit.service.JasperReportService;
import com.gameit.service.UserGameOrderService;
import com.gameit.service.UserService;
import com.gameit.web.dto.StripeBuyerToken;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
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

    @Autowired
    private JasperReportService jasperReportService;

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
    public ResponseEntity createCharge(@PathVariable String id, @RequestBody StripeBuyerToken stripeToken) {
        try {
            System.out.println(userService.getLoggedInUser());
            Game game = gameService.findById(id);
            UserGameOrder order = userGameOrderService.placeOrder(stripeToken.getToken(), stripeToken.getBuyer(), game);
            return ResponseEntity.ok(order);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "games/report", method = RequestMethod.GET)
    public void jasperGroupReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        try {
            JasperReportBuilder jrb = jasperReportService.createGamesReport();

            jrb.toPdf(out);
        } catch (DRException e) {
            throw new ServletException(e);
        }
        out.close();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
