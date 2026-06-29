package com.rajat.expense_tracker.service;

import com.rajat.expense_tracker.dto.request.CreateUserRequest;
import com.rajat.expense_tracker.dto.response.DeleteResponse;
import com.rajat.expense_tracker.dto.response.UserResponse;
import com.rajat.expense_tracker.entity.UserEntity;
import com.rajat.expense_tracker.exception.UserNotFoundException;
import com.rajat.expense_tracker.repository.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.PageRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public UserResponse createUserEntity(CreateUserRequest request){
        UserEntity user=new UserEntity();
        user.setName(request.name());
        user.setEmail(request.email());
        UserEntity savedUser=userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }
    public UserResponse getUserEntityById(Long id) throws UserNotFoundException{
        UserEntity user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
//        if(user.isPresent()){
//            UserEntity u=user.get();
//            UserResponse userResponse=new UserResponse(u.getId(),u.getName(),u.getEmail());
//        }
        return new UserResponse(user.getId(),user.getName(),user.getEmail());
    }
    @Transactional
    public UserResponse updateUserById(Long id,CreateUserRequest request) throws UserNotFoundException {
        UserEntity user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
//        if(user.isPresent()){
//            UserEntity u=user.get();
//            u.setName(request.name());
//            u.setEmail(request.email());
//        }
        user.setName(request.name());
        user.setEmail(request.email());
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public DeleteResponse deleteById(Long id) {
        UserEntity user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        userRepository.delete(user);
        return new DeleteResponse("User with id " +id+ " deleted succesfully ");
    }

//    public Page<UserResponse> getAllUsers(int page,int size){
//        Pageable pageable= PageRequest.of(page, size);
//        Page<UserEntity> users=userRepository.findAll(pageable);
//        Page<UserResponse> response=users.map(user->new UserResponse(user.getId(),user.getName(),user.getEmail()));
//        return response;
//    }
public Page<UserResponse> getAllUsers(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);

    Page<UserEntity> users = userRepository.findAll(pageable);

    Page<UserResponse> response = users.map(user ->
            new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            )
    );

    return response;
}
}
