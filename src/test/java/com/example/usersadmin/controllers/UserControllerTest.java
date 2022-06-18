package com.example.usersadmin.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.UUID;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.usersadmin.config.JwtTokenUtil;
import com.example.usersadmin.dto.UserRequestDTO;
import com.example.usersadmin.dto.UserResponseDTO;
import com.example.usersadmin.models.UserModel;
import com.example.usersadmin.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

	@Spy
	UserService userService;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;

	@Spy
	private ModelMapper modelMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@Before(value = "")
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void healthCheck() throws Exception {
		String response = "healthy";
		mockMvc.perform(get("/users/health-check")).andExpect(status().isOk()).andExpect(jsonPath("$").value(response));
	}

	@Test
	void createUser() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setName("user1");
		userModel.setEmail("new@hotmail.com");
		userModel.setPassword("aA1234567$");

		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setName("user1");
		userRequestDTO.setEmail("new@hotmail.com");
		userRequestDTO.setPassword("aA1234567$");
		userRequestDTO.setPhones(new ArrayList<>());

		UUID uuid = UUID.randomUUID();
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setId(uuid);

		final UserDetails userDetails = new User(userRequestDTO.getEmail(), userRequestDTO.getPassword(),
				new ArrayList<>());

		Mockito.when(modelMapper.map(userRequestDTO, UserModel.class)).thenReturn(userModel);
		Mockito.when(userService.saveUser(userModel)).thenReturn(userModel);
		Mockito.when(jwtTokenUtil.generateToken(userDetails)).thenReturn("eytoken");
		Mockito.when(modelMapper.map(userModel, UserResponseDTO.class)).thenReturn(userResponseDTO);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userRequestDTO))
				.characterEncoding("utf-8")).andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.token").isNotEmpty()).andExpect(jsonPath("$.token").value("eytoken"))
				.andExpect(status().isCreated());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}