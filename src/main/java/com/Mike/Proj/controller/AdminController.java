package com.Mike.Proj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.Mike.Proj.dto.UserDto;
import com.Mike.Proj.dto.cart.CartDto;
import com.Mike.Proj.model.User;
import com.Mike.Proj.service.AdminService;
import com.Mike.Proj.service.AuthenticationService;

@RestController
@CrossOrigin("http://localhost:8500")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AuthenticationService authenticationService;

    //get a list of all users
    @PostMapping("/users/")
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam ("token") String token){

        //find the user
        User user = authenticationService.getUser(token);

        //verify if user is an admin
        adminService.verifyAdminUser(user);

        List<UserDto> userDto = adminService.getUsers();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //get all cart items
    @GetMapping("/all-cart-items")
    public ResponseEntity<CartDto> getAllCartItems(@RequestParam("token") String token){
        //find user
        User user = authenticationService.getUser(token);

        //verify whether user is admin
        adminService.verifyAdminUser(user);

        CartDto cartDto = adminService.listCartBookings();
        
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}