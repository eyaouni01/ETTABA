package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.ConfirmationToken;
import com.intern.backendettaba.entities.Event;
import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.entities.User;
import com.intern.backendettaba.services.ImageService;
import com.intern.backendettaba.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.intern.backendettaba.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired

    private UserRepository userRepository;

    private final ImageService imageService;



    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    //public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Implémentation de l'enregistrement
       // return ResponseEntity.ok("Utilisateur enregistré");
    //}
    public User registerUser(@RequestBody @Valid User user) {
        User savedUser = userRepository.save(user);
        // Notifie tous les observateurs
        //userRegistrationSubject.notifyObservers("USER_REGISTERED", savedUser);
       return savedUser;
    }
    @PostMapping("/user")
    public ResponseEntity<User> add(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping(value = "/user/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> addImageToUser(@RequestPart("user") User user,
                                               @RequestPart("imageFile") MultipartFile file){
        try {
            Image image=imageService.uploadImage(file);
            user.setUserImage(image);
            return userService.updateUser(user.getId(),user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/user")
    public ResponseEntity<List<User>> list(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable(name = "id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/user/email")
    public ResponseEntity<User> getByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody User updatedUser){
        return userService.updateUser(id,updatedUser);
    }

    @PutMapping("/user/params/{id}")
    public void updateNameEmail(@PathVariable(name = "id") Long id,
                                                @RequestParam(required = false) String fname,
                                                @RequestParam(required = false) String lname,
                                                @RequestParam String email){
        userService.updateUserNameEmail(id,fname,lname,email);
    }

    ///////////////////////
    @GetMapping("/auth/getUserByEmail")
    public User findByEmail (@RequestParam(name = "email") String email){
        return userService.fetchUserByEmail(email);
    }

    @GetMapping("/auth/getUserByToken")
    public User findByToken (@RequestParam(name = "token") String token){
        return userService.fetchUserByToken(token);
    }
    @PostMapping("/auth/resetToken")
    public ConfirmationToken resetToken (@RequestParam(name = "token") String token) throws IllegalStateException {
        return userService.resetToken(token);
    }
    @GetMapping("/auth/existUserByEmail")
    public Boolean existUserByEmail (@RequestParam(name = "email") String email){
        return userService.existUser(email);
    }

    @GetMapping("/auth/existUserByToken")
    public Boolean existUserByToken (@RequestParam(name = "token") String token){
        return userService.existUserByToken(token);
    }
}
