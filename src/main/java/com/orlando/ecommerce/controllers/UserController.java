package com.orlando.ecommerce.controllers;

import com.orlando.ecommerce.entities.DTOs.ProductDTO;
import com.orlando.ecommerce.entities.DTOs.UserDTO;
import com.orlando.ecommerce.services.ProductService;
import com.orlando.ecommerce.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN','ROLE_CLIENT')")

    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> findMe() {
        UserDTO userDTO = userService.getMe();

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }


}