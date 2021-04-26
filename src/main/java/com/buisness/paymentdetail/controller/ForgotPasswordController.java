package com.buisness.paymentdetail.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buisness.paymentdetail.entity.User;
import com.buisness.paymentdetail.service.EmailService;
import com.buisness.paymentdetail.service.UserService;

@RestController
public class ForgotPasswordController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<?> processForgotPasswordForm(@RequestParam("email")String userEmail,HttpServletRequest request) {
		User user = userService.findByUsername(userEmail);
		HashMap<String,Boolean> map = new HashMap<String,Boolean>();
		if(user!=null) {
			if(user.getIsEnabled()) {
				user.setResetToken(UUID.randomUUID().toString());
				userService.updateUser(user);
				String appUrl = request.getScheme() + "://" + request.getServerName()+":"+4200;
				System.out.println(appUrl);
				
				SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
				passwordResetEmail.setFrom("AnuragApplication");
				passwordResetEmail.setTo(user.getUsername());
				passwordResetEmail.setSubject("Password Reset Request");
				passwordResetEmail.setText("To reset your password, click the link below:\n\n" + appUrl
						+ "/reset?token=" + user.getResetToken());
				
				emailService.sendEmail(passwordResetEmail);			
				map.put("success",true);
			}else {
				map.put("success",false);
			}
		}else {
			map.put("success",false);
		}
		return ResponseEntity.ok(map);
	
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ResponseEntity<?> displayResetPasswordPage(@RequestParam("token") String token) {
		
		Optional<User> opt = userService.findUserByResetToken(token);
		HashMap<String,Boolean> map = new HashMap<>();
		if (opt.isPresent()) { 
			map.put("flag", true);
			return ResponseEntity.ok(map);
		} else { // Token not found in DB
			map.put("flag", false);
			return ResponseEntity.ok(map);
		}
	}
	
	@PostMapping(value = "/reset")
	public ResponseEntity<?> setNewPassword(@RequestParam Map<String, String> requestParams) {

		// Find the user associated with the reset token
		Optional<User> opt = userService.findUserByResetToken(requestParams.get("token"));
		HashMap<String,Boolean> map = new HashMap<>();
		
		// This should always be non-null but we check just in case
		if (opt.isPresent()) {
			
			// Set new password    
            
			User user = opt.get();
			//user.setPassword(requestParams.get("password"));
			user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
			user.setResetToken(null);

			userService.updateUser(user);
			map.put("success", true);
			
		} else {
			map.put("success",false);	
		}
		return ResponseEntity.ok(map);
   }
	
}
