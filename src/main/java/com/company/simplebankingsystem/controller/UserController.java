package com.company.simplebankingsystem.controller;

import com.company.simplebankingsystem.dto.request.CreateUserRequest;
import com.company.simplebankingsystem.dto.request.UpdateUserRequest;
import com.company.simplebankingsystem.dto.response.UpdateUserResponse;
import com.company.simplebankingsystem.dto.response.UserResponse;
import com.company.simplebankingsystem.service.abstraction.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("update/{id}")
    @ResponseStatus(OK)
    public UpdateUserResponse updateUserById(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUserById(id, request);
    }

    @GetMapping("find/{id}")
    @ResponseStatus(OK)
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

}
