package com.example.execute.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.execute.entity.StateMaster;

public interface StateRepo extends JpaRepository<StateMaster, Serializable> {

	// select * from user_master where cuntryId=?
	public List<StateMaster> findByCountryId(Integer countryId);

}
