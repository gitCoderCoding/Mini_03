package com.example.execute.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.execute.entity.CityMaster;
import com.example.execute.entity.User;

public interface CityRepo extends JpaRepository<CityMaster, Serializable> {
//select * from user_master where stateId=?
	public List<CityMaster> findByStateId(Integer stateId);

}
