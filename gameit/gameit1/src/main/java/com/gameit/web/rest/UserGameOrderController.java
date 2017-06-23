package com.gameit.web.rest;

import com.gameit.model.UserGameOrder;
import com.gameit.service.GameService;
import com.gameit.service.JasperReportService;
import com.gameit.service.UserGameOrderService;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RepositoryRestController
public class UserGameOrderController {
    @Autowired
    private UserGameOrderService userGameOrderService;

    @Autowired
    private GameService gameService;

    @Autowired
    private JasperReportService jasperReportService;

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


    @RequestMapping("/orders/{id}/report")
    public void jasperReport(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        UserGameOrder order = userGameOrderService.findOne(id);
        OutputStream out = response.getOutputStream();
        try {
            JasperReportBuilder jrb = jasperReportService.createOrderReport(order);

            jrb.toPdf(out);
        } catch (DRException e) {
            throw new ServletException(e);
        }
        out.close();


    }
}
