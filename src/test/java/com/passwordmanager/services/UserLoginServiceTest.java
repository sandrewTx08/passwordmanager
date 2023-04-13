package com.passwordmanager.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.passwordmanager.models.Login;
import com.passwordmanager.models.User;
import com.passwordmanager.repositories.UserLoginRepository;
import com.passwordmanager.repositories.UserRepository;

@SpringBootTest
public class UserLoginServiceTest {
    @Autowired
    UserLoginService userLoginService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserLoginRepository userLoginRepository;

    @Test
    public void findUserLogins() {
        User user = new User();
        user.set_id(new ObjectId());
        user.setEmail("login@example.com");
        user.setPassword("password123");

        Login login = new Login();
        login.setUser(user);
        login.setWebsite("https://example.com");
        login.setPassword("admin123");

        when(userRepository.findById(user.get_id())).thenReturn(Optional.of(user));
        when(userLoginRepository.findUserLogins(user.get_id())).thenReturn(List.of(login));

        List<Login> userLogins = userLoginService.findUserLogins(user.get_id());

        assertEquals(login, userLogins.get(0));
    }
}