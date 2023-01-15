package com.example.execute.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.execute.binding.LoginForm;
import com.example.execute.binding.UnlockAccForm;
import com.example.execute.binding.UserForm;
import com.example.execute.entity.CityMaster;
import com.example.execute.entity.CountryMaster;
import com.example.execute.entity.StateMaster;
import com.example.execute.entity.User;
import com.example.execute.repo.CityRepo;
import com.example.execute.repo.CountryRepo;
import com.example.execute.repo.StateRepo;
import com.example.execute.repo.UserRepo;
import com.example.execute.util.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String checkEmail(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return "UNIQUE";
		}
		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryMaster> countries = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		countries.forEach(country -> {
			countryMap.put(country.getCountryId(), country.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {

		Map<Integer, String> stateMap = new HashMap<>();
		List<StateMaster> states = stateRepo.findByCountryId(countryId);
		states.forEach(state -> {
			stateMap.put(state.getCountryId(), state.getStateName());
		});
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		Map<Integer, String> cityMap = new HashMap<>();
		List<CityMaster> cities = cityRepo.findByStateId(stateId);
		cities.forEach(city -> {
			cityMap.put(city.getCityId(), city.getCityName());
		});
		return cityMap;

	}

	@Override
	public String registerUser(UserForm userForm) {
//copy data from binding obj to etity obj
		User entity = new User();
		BeanUtils.copyProperties(userForm, entity);
		entity.setUserPwd(generateRandomPwd());
		entity.setAccStatus("Locked");
		userRepo.save(entity);
		String to = userForm.getEmail();
		String subject = "Registration Email-Unlock Account";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
		emailUtils.sendEmail(to, subject, body);
		return "User Account Created";

	}

	private String generateRandomPwd() {
		String text = "ABCDEFGHIJKLMNOPQRSTUVVWXYZ1234567890";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int pwdLength = 6;
		for (int i = 1; i <= pwdLength; i++) {
			int index = random.nextInt(text.length());
			sb.append(text.charAt(index));
		}
		return sb.toString();
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockAccForm) {

		String email = unlockAccForm.getEmail();
		User user = userRepo.findByEmail(email);

		if (user != null && user.getUserPwd().equals(unlockAccForm.getTempPwd())) {
			user.setUserPwd(unlockAccForm.getNewPwd());
			user.setAccStatus("UNLOCKED");
			userRepo.save(user);
			return "Account Locked";
		}
		return "Invalid Temporary Password";

	}

	@Override
	public String login(LoginForm loginForm) {
		User user = userRepo.findByEmailandUserPwd(loginForm.getEmail(), loginForm.getPwd());

		if (user == null) {
			return "INVALID CREDENTIALS";
		}
		if (user.getAccStatus().equals("LOCKED")) {
			return "Account Locked";
		}
		return "SUCCESS";
	}

	@Override
	public String forgotPwd(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return "No Account  Found";
		}
		String subject = "Recover Password";
		String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", user);
		emailUtils.sendEmail(email, subject, body);
		return "Pasword sent to registered email";
	}

	private String readEmailBody(String filename, User user) {
		StringBuffer sb = new StringBuffer();

		try (Stream<String> lines = (Files.lines(Paths.get(filename)))) {

			lines.forEach(line -> {
				line = line.replace("${FirstName}", user.getFirstName());
				line = line.replace("${LastName}", user.getLastName());
				line = line.replace("${TempPassword}", user.getUserPwd());
				line = line.replace("${Email}", user.getEmail());
				line = line.replace("${Password}", user.getUserPwd());

				sb.append(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
