package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }



    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    @GetMapping("/users/{id}")
    public ResponseEntity <User> findOneUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUser(id).orElseThrow(() -> new UsernameNotFoundException("user is not exists")), HttpStatus.OK );
    }

    @PostMapping("/users")
    public ResponseEntity <User> createUser(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity <User> updateUser(@Valid @RequestBody User updateUser, BindingResult result) {
        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        userService.updateUser(updateUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping ("/users/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        User user = userService.findUser(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            userService.deleteUser(user.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
