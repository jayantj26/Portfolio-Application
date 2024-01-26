package com.portfolio.api.controller;

import com.portfolio.api.dto.HoldingsDto;
import com.portfolio.api.entity.User;
import com.portfolio.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody User user)
    {
        String response=userService.saveUser(user);
        if(response.equalsIgnoreCase("User already exist"))
        {
            return new ResponseEntity<String>(response,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }
    @GetMapping()
    public List<User>getAllUser()
    {
        return userService.getAllUser();
    }

    @GetMapping("/portfolio/{userId}")
    public ResponseEntity<?> getPortfolio(@PathVariable long userId)
    {
        HoldingsDto holdingsDto=userService.getPortfolio(userId);
        if(holdingsDto==null)return new ResponseEntity<>("No portfolio found for the given user",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userService.getPortfolio(userId),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(long userId)
    {
        return new ResponseEntity<User>(userService.getUser(userId),HttpStatus.OK);
    }

}
