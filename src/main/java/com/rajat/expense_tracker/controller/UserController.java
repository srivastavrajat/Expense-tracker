package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.request.CreateUserRequest;
import com.rajat.expense_tracker.dto.response.UserResponse;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.exception.UserNotFoundException;
import com.rajat.expense_tracker.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    UserResponse createUser(@Valid @RequestBody CreateUserRequest request){
        return userService.createUserEntity(request);
    }

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable Long id){
        return userService.getUserEntityById(id);
    }

    @PutMapping("/{id}")
    UserResponse updateUser(@PathVariable Long id,@Valid @RequestBody CreateUserRequest Updaterequest){
        return userService.updateUserById(id,Updaterequest);
    }
}
