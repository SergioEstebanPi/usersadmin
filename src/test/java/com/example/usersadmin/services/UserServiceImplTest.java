package com.example.usersadmin.services;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.usersadmin.exceptions.EmailInvalidException;
import com.example.usersadmin.exceptions.PasswordInvalidException;
import com.example.usersadmin.models.UserModel;
import com.example.usersadmin.repositories.UserRepository;

@SpringBootTest
class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	void whenValidEmailAndPasswordThenNoException() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setEmail("valid.email@hotmail.com");
		userModel.setPassword("Hello world$123");

		when(passwordEncoder.encode(userModel.getPassword()))
				.thenReturn("$2a$12$Xs3X55lOduv5P7OH2nBw2euoG.AcSBWiNKJ8XJ45YpDT/v4bXf5um");

		userServiceImpl.saveUser(userModel);

		verify(userRepository, times(1)).save(userModel);
		assertThatNoException();
	}

	@Test
	void whenInvalidEmailThenEmailException() {
		UserModel userModel = new UserModel();
		userModel.setEmail("wrongemail.com");
		userModel.setPassword("");

		Exception exception = assertThrows(EmailInvalidException.class, () -> {
			userServiceImpl.saveUser(userModel);
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
			userServiceImpl.saveUser(userModel);
		});

		String expectedMessage = "Invalid password requirements";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

}