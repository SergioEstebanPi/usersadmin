package com.example.usersadmin.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.usersadmin.models.PhoneModel;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneModel, UUID> {

	List<PhoneModel> findByUserModelId(UUID userModelId);

}
