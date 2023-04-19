package ru.kata.spring.boot_security.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<>(userService.getUsers().stream().map(this::convertToUserDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id) {
        return new ResponseEntity<>(convertToUserDto(userService.findById(id)), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity <User> createUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        userService.createUser(convertToUser(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity <User> updateUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        userService.updateUser(convertToUser(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    public User convertToUser(UserDto userDto) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);

    }

    public UserDto convertToUserDto(User user) {

          ModelMapper modelMapper = new ModelMapper();
          return modelMapper.map(user, UserDto.class);

    }
}
