package com.example.usersadmin.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.usersadmin.exceptions.EmailInvalidException;
import com.example.usersadmin.exceptions.PasswordInvalidException;
import com.example.usersadmin.models.UserModel;

@SpringBootTest
class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	void whenInvalidEmailThenEmailException() {
		UserModel userModel = new UserModel();
		userModel.setEmail("wrongemail.com");
		userModel.setPassword("");

		Exception exception = assertThrows(EmailInvalidException.class, () -> {
			userService.saveUser(userModel);
		});

		String expectedMessage = "Invalid email address format";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void whenInvalidPasswordThenPasswordException() {
		UserModel userModel = new UserModel();
		userModel.setEmail("validemail@hotmail.com");
		userModel.setPassword("123");

		Exception exception = assertThrows(PasswordInvalidException.class, () -> {
			userService.saveUser(userModel);
		});

		String expectedMessage = "Invalid password requirements";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

}