package com.magalu.favorites.product.controller;

import com.magalu.favorites.product.model.User;
import com.magalu.favorites.product.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository repository, PasswordEncoder encoder) {
        this.userRepository = repository;
        this.encoder = encoder;
        SaveDefaultLogin();
    }

    private void SaveDefaultLogin() {

        Optional<User> user = userRepository.findByLogin("magalu");
        if(user.isPresent()){
            user.get().setPassword(encoder.encode("magalu123"));
            userRepository.save(user.get());
        }
        else {
            User newUser = User.builder().login("magalu").password("magalu123").build();
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/validatePwd")
    public ResponseEntity<Boolean> validatePwd(@RequestParam String login,
                                                @RequestParam String password) {

        Optional<User> optUser = userRepository.findByLogin(login);
        if (!optUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        User user = optUser.get();
        boolean valid = encoder.matches(password, user.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

}
