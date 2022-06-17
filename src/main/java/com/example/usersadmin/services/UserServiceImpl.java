package com.example.usersadmin.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usersadmin.exceptions.EmailInvalidException;
import com.example.usersadmin.models.PhoneModel;
import com.example.usersadmin.models.UserModel;
import com.example.usersadmin.repositories.PhoneRepository;
import com.example.usersadmin.repositories.UserRepository;
import com.example.usersadmin.util.Validator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	public List<UserModel> findUsers() {
		List<UserModel> users = (List<UserModel>) userRepository.findAll();
		if (users != null) {
			users.forEach((user) -> {
				user.setPhones((List<PhoneModel>) phoneRepository.findByUserModelId(user.getId()));
			});
		}
		return users;
	}

	public UserModel saveUser(UserModel userModel) throws Exception {
		if (!Validator.validateEmail(userModel.getEmail())) {
			throw new EmailInvalidException("Invalid email address format");
		}
		if (!Validator.validatePassword(userModel.getPassword())) {
			throw new Exception("Invalid password requirements");
		}
		List<PhoneModel> userPhones = userModel.getPhones();
		if (userPhones != null) {
			userPhones.forEach((userPhone) -> {
				userPhone.setUserModel(userModel);
			});
		}
		userModel.setCreatedAt(new Date());
		userModel.setCreatedBy("ADMIN");
		userModel.setModifiedAt(new Date());
		userModel.setModifiedBy("ADMIN");
		userModel.setLastLogin(new Date());
		return userRepository.save(userModel);
	}

	public UserModel updateUser(UUID id, UserModel userModel) {
		UserModel oldUserModel = userRepository.findById(id).orElseThrow();
		oldUserModel.setName(userModel.getName());
		oldUserModel.setEmail(userModel.getEmail());
		oldUserModel.setPassword(userModel.getPassword());
		oldUserModel.setPhones(userModel.getPhones());
		oldUserModel.setModifiedAt(new Date());
		oldUserModel.setModifiedBy("ADMIN");
		return userRepository.save(oldUserModel);
	}

	public void deleteUser(UUID id) {
		UserModel userModel = userRepository.findById(id).orElseThrow();
		userRepository.delete(userModel);
	}

}
