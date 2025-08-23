package org.example.killhimka.service.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.killhimka.dto.user.InputUserDto;
import org.example.killhimka.dto.user.RegistrationUserDto;
import org.example.killhimka.dto.user.UserDto;
import org.example.killhimka.entity.User;
import org.example.killhimka.mapper.user.UserMapper;
import org.example.killhimka.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto registerUser (RegistrationUserDto registrationUserDto){

        User user = null;

        user = userMapper.toEntity(registrationUserDto);
        user = userRepository.save(user);

        UserDto userDto = userMapper.toDto(user);

        return userDto;
    }

    public UserDto inputUser (InputUserDto inputUserDto){

        User user = userRepository.findByLogin(inputUserDto.getLogin()).orElseThrow(() ->
                new EntityNotFoundException("The username or password is incorrect."));

        if(!user.getPassword().equals(inputUserDto.getPassword())){
            throw new EntityNotFoundException("The username or password is incorrect.");
        }
        UserDto userDto = userMapper.toDto(user);

        return userDto;
    }

}
