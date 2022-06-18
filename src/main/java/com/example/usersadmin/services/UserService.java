package com.example.usersadmin.services;

import java.util.List;
import java.util.UUID;

import com.example.usersadmin.models.UserModel;

public interface UserService {

	public List<UserModel> findUsers();

	public UserModel saveUser(UserModel userModel) throws Exception;

	public UserModel updateUser(UUID id, UserModel userModel) throws Exception;

	public void deleteUser(UUID id) throws Exception;

}
