package com.gameit.web.rest;

import com.gameit.model.Authority;
import com.gameit.model.User;
import com.gameit.model.exceptions.EmailExistsException;
import com.gameit.repository.AuthorityRepository;
import com.gameit.service.JasperReportService;
import com.gameit.service.UserService;
import com.gameit.web.dto.UserWithAuthorities;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RepositoryRestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private JasperReportService jasperReportService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.findAll();
        ResponseEntity<?> responseEntity;
        if (users.size() > 0) {
            responseEntity = ResponseEntity.ok(users);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }
        return responseEntity;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneUser(@PathVariable String id) {
        User user = userService.findById(id);
        ResponseEntity<?> responseEntity;
        if (user != null) {
            responseEntity = ResponseEntity.ok(user);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }
        return responseEntity;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOneUser(@PathVariable String id) {
        userService.delete(id);
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

//    @RequestMapping(value = "/users", method = RequestMethod.POST)
//    public ResponseEntity<?> createNewUser(@RequestBody UserWithAuthorities userObject) throws IOException, SQLException, EmailExistsException {
//        ArrayList<Authority> authorities = new ArrayList<>();
//        authorities = userObject.getAuthorities()
//                .stream()
//                .map(authority -> authorityRepository.findOne(authority))
//                .collect(Collectors.toCollection(ArrayList<Authority>::new));
//
//        User user = userService.create(userObject.getUser(), authorities);
//        final URI location = ServletUriComponentsBuilder.
//                fromCurrentServletMapping().path("/users/{id}").build()
//                .expand(user.getId()).toUri();
//        return ResponseEntity.created(location).body(user);
//    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User userObject) {
        User beforeUpdate = userService.findById(id);
        User user = userService.update(beforeUpdate, userObject);
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/users/report")
    public void jasperReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        try {
            JasperReportBuilder jrb = jasperReportService.createUsersReport();

            jrb.toPdf(out);
        } catch (DRException e) {
            throw new ServletException(e);
        }
        out.close();
    }


}
