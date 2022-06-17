package com.example.usersadmin.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.usersadmin.models.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, UUID> {

}
