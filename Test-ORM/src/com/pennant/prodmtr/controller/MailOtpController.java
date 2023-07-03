package com.pennant.prodmtr.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pennant.prodmtr.Dao.Interface.UserDao;
import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.model.view.emailSend;

@Controller
public class MailOtpController {
	String generateotp;
	String finalemail;
	List<User> u = new ArrayList<>();
	UserDao UserDao;
	private static final Logger LOGGER = LogManager.getLogger(MailOtpController.class);

	@Autowired
	public MailOtpController(UserDao UserDao) {
		this.UserDao = UserDao;
	}

	@RequestMapping(value = "/forgetpassword", method = RequestMethod.GET)
	public String forgotPswd(Model model) {
		LOGGER.info("Forgot password page requested");
		return "forgetpassword";
	}

	/*
	 * @RequestMapping(value = "/otpAction") public String sendOTP(@RequestParam("email") String email) {
	 * System.out.println(email); u = userdao.getuserbyemailid(email); generateotp = (new emailSend()).sendEmail(email);
	 * 
	 * return generateotp; // Return the generated OTP as the response }
	 */
	@RequestMapping(value = "/otpActionsend")
	@ResponseBody
	public String sendOTP(@RequestParam("email") String email) {
		System.out.println(email);
		LOGGER.info("Sending OTP for email: {}", email);
		u = UserDao.getuserbyemailid(email);
		generateotp = (new emailSend()).sendEmail(email);

		return generateotp;
	}

	@RequestMapping(value = "/emailValid", method = RequestMethod.GET)
	@ResponseBody
	public String verifyEmail(@RequestParam("email") String email) {
		LOGGER.info("Verifying email: {}", email);
		u = UserDao.getuserbyemailid(email);
		if (u == null)
			return "no";
		return "yes"; // Return the generated OTP as the response
	}

	@RequestMapping(value = "/validateOTP", method = RequestMethod.GET)
	@ResponseBody
	public String validateOTP(@RequestParam("otp12") String otp) {
		 LOGGER.info("Validating OTP: {}", otp);

		if (otp.equals(generateotp)) {
			// java.sql.Timestamp timestamp1 = cust.getCustLastLoginDate();
			java.time.LocalDateTime t1 = java.time.LocalDateTime.now(); // Remove the data type declaration here
			java.time.LocalDateTime t2 = java.time.LocalDateTime.now();
			java.time.Duration duration = java.time.Duration.between(t1, t2);
			// System.out.println("t1: " + t1);
			// System.out.println("t2: " + t2);
			// System.out.println();
			long seconds = duration.getSeconds();
			// System.out.println("seconds: " + seconds);
			if (seconds <= 30) {
				return "valid";
			} else {
				return "no";
			}
		} else {
			// OTP is invalid
			return "invalid";
		}
	}

	@RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
	public String usersignup(@RequestParam("email") String email, @RequestParam("password") String password) {
		LOGGER.info("Updating password for email: {}", email);
		UserDao.forgotPassword(email, password);
		return "login";
	}
}
