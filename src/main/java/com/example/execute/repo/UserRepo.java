package com.example.execute.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.execute.entity.User;

public interface UserRepo extends JpaRepository<User, Serializable> {

	// select * from user_master where email=?
	public User findByEmail(String email);

	// select * from user_master where email =? and user_pwd=?
	public User findByEmailandUserPwd(String email, String pwd);

	
}
