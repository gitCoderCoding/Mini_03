package com.example.execute.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.execute.entity.CountryMaster;

public interface CountryRepo extends JpaRepository<CountryMaster, Serializable> {

}
