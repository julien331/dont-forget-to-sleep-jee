package ch.hearc.dfts.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.hearc.dfts.dto.UserDto;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.VerificationToken;
import ch.hearc.dfts.models.services.IUserService;
import ch.hearc.dfts.models.services.UserService;
import ch.hearc.dfts.registration.OnRegistrationCompleteEvent;
import ch.hearc.dfts.security.SecurityService;
import ch.hearc.dfts.validators.UserValidator;

@Controller
public class UserController {

	@Autowired
	SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private MessageSource messages;

	@Autowired
	private IUserService service;

	private static final String REGISTRATION_FORM_PATH = "registration/registration.html";
	private static final String INDEX_PATH = "redirect:/";
	
	private static final String BADUSER_PATH = "registration/badUser";
	private static final String EMAILERROR_PATH = "registration/emailError";
	private static final String EMAILSUCESS_PATH = "registration/successRegister";

	@RequestMapping(value="/login", method = RequestMethod.GET )
	public String loginPage(WebRequest request, Model model) {
		return "registration/login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		return REGISTRATION_FORM_PATH;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(@Valid UserDto userDto, BindingResult result, WebRequest request) {

		userValidator.validate(userDto, result);
	

		if (result.hasErrors()) {
			return userService.manageErrorUser(result,REGISTRATION_FORM_PATH,userDto);
		}

		User user = new User(userDto);

		userService.save(user);

		try {
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
		} catch (Exception me) {
			return new ModelAndView(EMAILERROR_PATH, "user", user);
		}
		return new ModelAndView(EMAILSUCESS_PATH, "user", user);
	}

	@GetMapping("/registrationConfirm")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = service.getVerificationToken(token);

		if (verificationToken == null) {
			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return BADUSER_PATH;
		}

		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			String messageValue = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", messageValue);
			return BADUSER_PATH;
		}

		user.setEnabled(true);
		service.saveRegisteredUser(user);
		
		return INDEX_PATH;
	}
	
	@GetMapping("/user")
	public String updateUser(WebRequest request, Model model) {
		
		User user = userService.getCurrentUser();
		
		UserDto userDto = new UserDto(user);
		
		model.addAttribute("userDto", userDto);
		model.addAttribute("user_name",user.getName());
		
		
		return "updateuser";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView updateUser(@Valid UserDto userDto, BindingResult result, WebRequest request) {
		
		userValidator.validatePassword(userDto, result);
		User user = userService.getCurrentUser();
		
		if (result.hasErrors()) {
			

			ModelAndView modelAndView =  new ModelAndView("updateuser", "userDto",new UserDto(user));
			
			List<String> errorCodesList = new ArrayList<String>();
			
			for(ObjectError err : result.getAllErrors()) {
				errorCodesList.add(err.getCode());
			}
			
			modelAndView.addObject("errors", errorCodesList);
			modelAndView.addObject("user_name",user.getName());
			return modelAndView;
		}
		
		user.setPassword(userDto.getPassword());
		userService.updatePassword(user);
		
		return new ModelAndView("redirect:/");
	}
}
