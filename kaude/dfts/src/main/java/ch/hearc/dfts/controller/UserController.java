package ch.hearc.dfts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import ch.hearc.dfts.DTO.UserDto;

@Controller
public class UserController {
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
	    UserDto userDto = new UserDto();
	    model.addAttribute("user", userDto);
	    return "registration/registration.html";
	}

}
