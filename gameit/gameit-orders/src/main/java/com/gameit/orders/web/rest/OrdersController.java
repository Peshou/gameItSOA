package com.gameit.orders.web.rest;

import com.gameit.orders.model.User;
import com.gameit.orders.model.UserGameOrder;
import com.gameit.orders.model.exceptions.StorageFileNotFoundException;
import com.gameit.orders.service.UserGameOrderService;
import com.gameit.orders.web.dto.StripeBuyerToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserGameOrder>> getOrdersForUser(@PathVariable String id, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size) {
        if (page != null && size != null) {
            Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
            Pageable pageable = new PageRequest(page, size, sort);
            return ResponseEntity.ok(userGameOrderService.getAllOrders(id, pageable));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
