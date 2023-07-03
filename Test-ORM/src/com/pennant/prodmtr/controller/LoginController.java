package com.pennant.prodmtr.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pennant.prodmtr.model.Entity.User;
import com.pennant.prodmtr.service.Interface.UserService;

@Controller
public class LoginController {
	@Autowired
	UserService userService;
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndex(Model model) {
		LOGGER.info("entered");
		return "login";
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String verifyUser(@Validated User user, Model model, HttpSession session) {
		try {

			LOGGER.debug("Verifying user: " + user.getUserEmployeeId());
			String page = userService.verifyUser(user, session);
			return page;
		} catch (Exception e) {
			LOGGER.error("An error occurred while verifying the user: " + e.getMessage());
			// Handle the exception, e.g., show an error page or redirect to an error page
			model.addAttribute("errorMessage", "An error occurred during user verification.");
			return "error";
		}
	}

	@RequestMapping(value = "/a", method = RequestMethod.GET)
	public String a(Model model, HttpSession session) {
		try {
			User user = new User();
			user.setUserEmployeeId("1");
			user.setUserDisplayName("bhargav");
			user.setUserPassword("hello");

			session.setAttribute("user", user);
			LOGGER.info("User session created for user: " + user.getUserEmployeeId());
			return "a";
		} catch (Exception e) {
			LOGGER.error("An error occurred while creating the user session: " + e.getMessage());
			// Handle the exception, e.g., show an error page or redirect to an error page
			model.addAttribute("errorMessage", "An error occurred during user session creation.");
			return "error";
		}
	}

	@RequestMapping(value = "getEmail", method = RequestMethod.GET)
	public String getEmail(Model model, HttpSession session) {
		try {
			User user = new User();
			user.setUserEmployeeId("1");
			user.setUserDisplayName("bhargav");
			user.setUserPassword("hello");
			session.setAttribute("user", user);
			LOGGER.debug("Getting email for user: " + user.getUserEmployeeId());

			return "a";
		} catch (Exception e) {
			LOGGER.error("An error occurred while getting email for the user: " + e.getMessage());

			model.addAttribute("errorMessage", "An error occurred during email retrieval.");
			return "error";
		}
	}
}
