package io.jans.metamask;

import io.jans.as.common.service.common.UserService;
import io.jans.service.cdi.util.CdiUtil;
import io.jans.util.StringHelper;
import io.jans.as.common.model.common.User;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

	private static final Logger logger = LoggerFactory.getLogger(UserCheck.class);
	private static final UserService userService = CdiUtil.bean(UserService);
	private static Map<String, String> configAttributes;

	public Utility() {
	}

	public static boolean initializeFlow(Map<String, String> config) {
		logger.info("METAMASK_AUTH. Initialization");
		configAttributes = config;
		if (StringHelper.isEmpty(configAttributes.get("APP_TITLE"))) {
			logger.info("METAMASK_AUTH. Initialization. Property APP_TITLE is mandatory");
			return false;
		}
		logger.info("METAMASK_AUTH. Initialization. Completed");
	}

	public static boolean addUser(boolean userExists, String uid) {
		User resultUser = userService.getUserByAttribute("uid", uid);
		logger.info("userExists:" + resultUser);
		if (resultUser == null) {
			logger.info("METAMASK_AUTH. Adding user: " + uid);
			User user = new User();
			user.setAttribute("uid", uid);
			user = userService.addUser(user, true);
			logger.info("User has been added - " + uid);
		} else {
			return true;
		}
	}

	public static boolean isUserExists(String uid) {
		logger.info("METAMASK_AUTH. Checking userExists username: " + uid);
		if (uid == null || uid.isBlank()) {
			logger.info("METAMASK_AUTH. Checking userExists false ");
			return false;
		} else {    
			User resultUser = userService.getUserByAttribute("uid", uid);
			logger.info("METAMASK_AUTH. isUserExists:" + resultUser);
			if (resultUser == null)
				return false;
			else
				return true;
		}
	}
}