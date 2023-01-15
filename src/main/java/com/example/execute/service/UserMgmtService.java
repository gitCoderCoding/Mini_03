package com.example.execute.service;

import java.util.Map;

import com.example.execute.binding.LoginForm;
import com.example.execute.binding.UnlockAccForm;
import com.example.execute.binding.UserForm;
import com.example.execute.entity.User;

public interface UserMgmtService {
	public String checkEmail(String email);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public String registerUser(UserForm userForm);

	public String unlockAccount(UnlockAccForm accForm);

	public String login(LoginForm loginForm);

	public String forgotPwd(String email);
}
