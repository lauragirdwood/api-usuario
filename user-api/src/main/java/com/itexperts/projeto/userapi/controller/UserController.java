package com.itexperts.projeto.userapi.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itexperts.projeto.userapi.model.User;
import com.itexperts.projeto.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {

        User u = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateWithReturn(@PathVariable Long id, @RequestBody User user) {

        User u = userService.updateWithReturn(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }

    @PutMapping("/{id}/update-sem-retorno")
    public ResponseEntity<Void> updateWithoutReturn(@PathVariable Long id, @RequestBody User user) {

        userService.updateWithoutReturn(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Teste Patch sem DTO
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateOnlyUserLastName(@PathVariable Long id, @RequestBody String lastName) {

        User user = userService.updateOnlyUserLastName(id, lastName);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {

        User user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllList() {

        List<User> users = userService.getAllWithoutPageable();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<User>> getAllPage(Pageable pageable) {

        Page<User> users = userService.getAllWithPageable(pageable);
        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
