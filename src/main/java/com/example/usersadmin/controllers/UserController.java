package com.example.usersadmin.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usersadmin.config.JwtTokenUtil;
import com.example.usersadmin.dto.UserRequestDTO;
import com.example.usersadmin.dto.UserResponseDTO;
import com.example.usersadmin.models.UserModel;
import com.example.usersadmin.services.UserService;

@RequestMapping("/users")
@RestController
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping("/health-check")
	public String healthCheck() {
		return "healthy";
	}

	@GetMapping()
	public List<UserResponseDTO> getUsers() {
		List<UserResponseDTO> userResponses = userService.findUsers().stream()
				.map(userResponse -> modelMapper.map(userResponse, UserResponseDTO.class)).collect(Collectors.toList());
		return userResponses;
	}

	@PostMapping()
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) throws Exception {
		final UserDetails userDetails = new User(userRequestDTO.getEmail(), userRequestDTO.getPassword(),
				new ArrayList<>());
		UserModel userModel = modelMapper.map(userRequestDTO, UserModel.class);
		userModel.setToken(jwtTokenUtil.generateToken(userDetails));
		UserResponseDTO userResponse = modelMapper.map(userService.saveUser(userModel), UserResponseDTO.class);
		return new ResponseEntity<UserResponseDTO>(userResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UserRequestDTO userRequestDTO)
			throws Exception {
		UserModel userModel = modelMapper.map(userRequestDTO, UserModel.class);
		UserResponseDTO userResponse = modelMapper.map(userService.updateUser(id, userModel), UserResponseDTO.class);
		return ResponseEntity.ok().body(userResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID id) throws Exception {
		userService.deleteUser(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "User with id: " + id + " successfuly deleted");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}
}
