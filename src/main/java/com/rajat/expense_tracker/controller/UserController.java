package com.rajat.expense_tracker.controller;

import com.rajat.expense_tracker.dto.request.CreateUserRequest;
import com.rajat.expense_tracker.dto.response.DeleteResponse;
import com.rajat.expense_tracker.dto.response.UserResponse;
import com.rajat.expense_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request){
        return userService.createUserEntity(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userService.getUserEntityById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,@Valid @RequestBody CreateUserRequest Updaterequest){
        return userService.updateUserById(id,Updaterequest);
    }
    @DeleteMapping("/{id}")
    public DeleteResponse deleteUser(@PathVariable Long id){
        return userService.deleteById(id);
    }
    @GetMapping
    public Page<UserResponse> getAllUsers(@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue ="10")int size){
        return userService.getAllUsers(page,size);
    }
}
