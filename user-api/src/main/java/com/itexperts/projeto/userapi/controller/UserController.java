package com.itexperts.projeto.userapi.controller;

import com.itexperts.projeto.userapi.dto.UserLastNameRequestDTO;
import com.itexperts.projeto.userapi.dto.UserRequestDTO;
import com.itexperts.projeto.userapi.dto.UserResponseDTO;
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

    //SEM DTO
//    @PostMapping
//    public ResponseEntity<User> create(@RequestBody User user) {
//
//        User u = userService.create(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(u);
//    }

    //COM DTO
    @PostMapping
    public ResponseEntity<UserResponseDTO> createWithDTO(@RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO userResponseDTO = userService.createComDTOV3(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    //SEM DTO
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateWithReturn(@PathVariable Long id, @RequestBody User user) {
//
//        User u = userService.updateWithReturn(id, user);
//        return ResponseEntity.status(HttpStatus.OK).body(u);
//    }

    //COM DTO
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateWithReturnDTO(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO userResponseDTO = userService.updateWithReturnDTOV3(id, userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    //SEM DTO
//    @PutMapping("/{id}/update-sem-retorno")
//    public ResponseEntity<Void> updateWithoutReturn(@PathVariable Long id, @RequestBody User user) {
//
//        userService.updateWithoutReturn(id, user);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

    //COM DTO
    @PutMapping("/{id}/update-sem-retorno")
    public ResponseEntity<Void> updateWithoutReturn(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {

        userService.updateWithoutReturnDTOV2(id, userRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //SEM DTO
//    @PatchMapping("/{id}/sem-dto")
//    public ResponseEntity<User> updateOnlyUserLastNameWithoutDTO(@PathVariable Long id, @RequestBody String lastName) {
//
//        User user = userService.updateOnlyUserLastNameWithoutDTO(id, lastName);
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }

    //COM DTO
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateOnlyUserLastNameWitDTO(
            @PathVariable Long id, @RequestBody UserLastNameRequestDTO userLastNameRequestDTO) {

        UserResponseDTO userResponseDTO = userService.updateOnlyUserLastNameWithDTOV1(id, userLastNameRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    //SEM DTO
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getById(@PathVariable Long id) {
//
//        User user = userService.getById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }

    //COM DTO
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByIdWithDTO(@PathVariable Long id) {

        UserResponseDTO userResponseDTO = userService.getByIdWithDTOV3(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    //SEM DTO
//    @GetMapping("/list")
//    public ResponseEntity<List<User>> getAllList() {
//
//        List<User> users = userService.getAllWithoutPageable();
//        return ResponseEntity.ok().body(users);
//    }

    //COM DTO
    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> getAllList() {

        List<UserResponseDTO> users = userService.getAllWithoutPageableWithDTOV3();
        return ResponseEntity.ok().body(users);
    }

    //SEM DTO
//    @GetMapping("/page")
//    public ResponseEntity<Page<User>> getAllPage(Pageable pageable) {
//
//        Page<User> users = userService.getAllWithPageable(pageable);
//        return ResponseEntity.ok().body(users);
//    }

    //COM DTO
    @GetMapping("/page")
    public ResponseEntity<Page<UserResponseDTO>> getAllPage(Pageable pageable) {

        Page<UserResponseDTO> users = userService.getAllWithPageableWithDTOV3(pageable);
        return ResponseEntity.ok().body(users);
    }

    //SEM DTO pq n precisa de DTO nem de request nem de response
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
