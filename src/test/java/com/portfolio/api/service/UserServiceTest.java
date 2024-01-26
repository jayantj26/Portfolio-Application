package com.portfolio.api.service;

import com.portfolio.api.entity.User;
import com.portfolio.api.exception.ResourceNotFoundException;
import com.portfolio.api.repository.UserRepository;
import com.portfolio.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User existingUser;
    private User newUser;

    @BeforeEach
    void setUp() {
        existingUser = User.builder()
                .userId(1)
                .userName("Jayant26")
                .build();

        newUser = User.builder()
                .userId(10)
                .userName("randomUser")
                .build();
    }

    @Test
    void whenUserNotExisted_ThenSaveUser() throws ResourceNotFoundException {
        // Arrange
        Mockito.when(userRepository.findByUserName(newUser.getUserName())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

        // Act
        String result = userService.saveUser(newUser);

        // Assert
        assertEquals( "User created successfully",result );
    }

    @Test
    void whenUserAlreadyExists_thenReturnUserAlreadyExist() {
        Mockito.when(userRepository.findByUserName(existingUser.getUserName())).thenReturn(Optional.of(existingUser));
        String result = userService.saveUser(existingUser);
        assertEquals("User already exist", result);
    }

    @Test
    void whenRightIdProvided_thenReturnUser() throws ResourceNotFoundException {
        Mockito.when(userRepository.findById(existingUser.getUserId())).thenReturn(Optional.of(existingUser));
        User foundUser = userService.getUser(existingUser.getUserId());
        assertEquals(existingUser, foundUser);
    }

    @Test
    void whenWrongIdProvided_thenThrowUserNotFoundException() {
        when(userRepository.findById(newUser.getUserId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUser(newUser.getUserId()));
    }
}
