package com.gameit.web.rest;

import com.gameit.model.UserGameOrder;
import com.gameit.service.GameService;
import com.gameit.service.UserGameOrderService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class UserGameOrderController {
    @Autowired
    private UserGameOrderService userGameOrderService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ResponseEntity getAllUserOrders(@RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size) {
        try {
            return ResponseEntity.ok(userGameOrderService.getAllOrders(new PageRequest(page,size)));
        }
        catch(Exception e ){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
