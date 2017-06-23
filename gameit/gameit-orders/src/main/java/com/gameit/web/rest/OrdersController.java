package com.gameit.web.rest;

import com.gameit.model.UserGameOrder;
import com.gameit.model.exceptions.StorageFileNotFoundException;
import com.gameit.service.UserGameOrderService;
import com.gameit.web.dto.StripeBuyerToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrdersController {
    @Autowired
    private UserGameOrderService userGameOrderService;

    @PostMapping(value = "/games/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserGameOrder createCharge(@RequestBody StripeBuyerToken stripeToken) {
        try {
            UserGameOrder order = userGameOrderService.placeOrder(stripeToken.getToken(), stripeToken.getBuyer(), stripeToken.getGame());
            return order;
        } catch (Exception e) {
            return null;
        }
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
