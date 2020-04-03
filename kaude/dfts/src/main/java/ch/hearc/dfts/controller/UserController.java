package ch.hearc.dfts.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import ch.hearc.dfts.SecurityConfiguration;
import ch.hearc.dfts.dto.UserDto;
import ch.hearc.dfts.exceptions.MotDePasseException;
import ch.hearc.dfts.models.Role;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.repositories.RoleRepository;
import ch.hearc.dfts.models.repositories.UserRepository;

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

	private static final String REGISTRATION_FORM_PATH = "registration/registration.html";
	private static final String INDEX_PATH = "redirect:/";
	private static final String LOGIN_PATH = "redirect:/login";

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return REGISTRATION_FORM_PATH;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String addPersonne(@Valid UserDto userDTO, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return REGISTRATION_FORM_PATH;
		}

		User newUser = null;

		try {
			newUser = verifiyInput(userDTO);
		} catch (MotDePasseException e) {
			userDTO.setPassword("");
			userDTO.setMatchingPassword("");

			model.addAttribute("user", userDTO);
			model.addAttribute("erreur", e.getMessage());

			return REGISTRATION_FORM_PATH;
		}

		Role roleUser = roleRepo.findByName("ROLE_ADMIN");

		Set<Role> rolesUser = new HashSet<>();
		rolesUser.add(roleUser);
		newUser.setRoles(rolesUser);

		userRepo.save(newUser);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(newUser.getName(),
				userDTO.getPassword());

		try {
			securityConfig.authenticationManagerBean().authenticate(authToken);
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(authToken);

		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("user", userDTO);
			return REGISTRATION_FORM_PATH;
		}

		return INDEX_PATH;
	}

	private User verifiyInput(UserDto userDTO) throws MotDePasseException {

		if (!(userDTO.getPassword().equals(userDTO.getMatchingPassword()))) {
			throw new MotDePasseException("Les mots de passe ne correspondent pas");
		}
		User userToAdd = new User();

		userToAdd.setEmail(userDTO.getEmail());
		userToAdd.setName(userDTO.getName());
		userToAdd.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

		return userToAdd;
	}

}
