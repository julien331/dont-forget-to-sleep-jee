package ch.hearc.dfts.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import ch.hearc.dfts.dto.UserDto;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.VerificationToken;
import ch.hearc.dfts.models.repositories.RoleRepository;
import ch.hearc.dfts.models.repositories.UserRepository;
import ch.hearc.dfts.models.services.IUserService;
import ch.hearc.dfts.models.services.UserService;
import ch.hearc.dfts.registration.OnRegistrationCompleteEvent;
import ch.hearc.dfts.security.SecurityConfiguration;
import ch.hearc.dfts.security.SecurityService;
import ch.hearc.dfts.validators.UserValidator;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	SecurityConfiguration securityConfig;
	
	@Autowired
	SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	private MessageSource messages;

	@Autowired
	private IUserService service;

	private static final String REGISTRATION_FORM_PATH = "registration/registration.html";
	private static final String INDEX_PATH = "redirect:/";
	private static final String LOGIN_PATH = "redirect:/login";

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
			ModelAndView modelAndView =  new ModelAndView(REGISTRATION_FORM_PATH, "userDto",userDto);
			
			List<String> errorCodesList = new ArrayList<String>();
			
			for(ObjectError err : result.getAllErrors()) {
				errorCodesList.add(err.getCode());
			}
			
			modelAndView.addObject("errors", errorCodesList);
			return modelAndView;
		}

		User user = new User(userDto);

		userService.save(user);

		try {
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
		} catch (Exception me) {
			System.out.println(me.getMessage());
			return new ModelAndView("registration/emailError", "user", user);
		}
		return new ModelAndView("registration/successRegister", "user", user);
	}

	@GetMapping("/registrationConfirm")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		Locale locale = request.getLocale();

		VerificationToken verificationToken = service.getVerificationToken(token);

		if (verificationToken == null) {
			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			return "registration/badUser";
		}

		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			String messageValue = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", messageValue);
			return "registration/badUser";
		}

		user.setEnabled(true);
		service.saveRegisteredUser(user);
		securityService.autoLogin(user.getName(), user.getPassword());
		
		return INDEX_PATH;
	}

}
