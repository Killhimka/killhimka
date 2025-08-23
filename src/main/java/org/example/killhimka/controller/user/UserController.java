package org.example.killhimka.controller.user;

import lombok.RequiredArgsConstructor;
import org.example.killhimka.dto.user.InputUserDto;
import org.example.killhimka.dto.user.RegistrationUserDto;
import org.example.killhimka.dto.user.UserDto;
import org.example.killhimka.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationUserDto registrationUserDto){
        UserDto userDto = userService.registerUser(registrationUserDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody InputUserDto inputUserDto){
        UserDto userDto = userService.inputUser(inputUserDto);
        return ResponseEntity.ok(userDto);
    }
}
