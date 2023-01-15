package com.example.execute.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "COUNTRY_MASTER")
public class CountryMaster {
	@Id
	private Integer countryId;
	private String countryName;
}
