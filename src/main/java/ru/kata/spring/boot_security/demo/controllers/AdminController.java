package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
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
    public ResponseEntity<List<User>> getUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("formUser", new User());
        List<Role> roles = roleService.getRolesList();
        model.addAttribute("allRoles", roles);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity <User> findOneUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUser(id).orElseThrow(() -> new UsernameNotFoundException("user is not exists")), HttpStatus.OK );
    }

    @PostMapping("/adduser")
    public ResponseEntity <User> createUser(@Validated(User.class) @RequestBody User user,
                           @RequestParam("role") List<String> values,
                             BindingResult result) {
        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        Set<Role> roleSet = userService.getSetOfRoles(values);
        user.setRoles(roleSet);
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity <User> updateUser(@Validated(User.class) @RequestBody User updateUser,
    @RequestParam("role") List<String> values,
    BindingResult result) {
        if(result.hasErrors()) {
            throw new IllegalArgumentException();
        }
        Set<Role> roleSet = userService.getSetOfRoles(values);
        updateUser.setRoles(roleSet);
        userService.updateUser(updateUser);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        User user = userService.findUser(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
            userService.deleteUser(user.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
