package ch.hearc.dfts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.repositories.TaskRepository;
import ch.hearc.dfts.models.services.UserDetailServiceImpl;


@Controller
public class HomeController {
	
	@Autowired
	TaskRepository taskRepo;
	@Autowired
	private UserDetailServiceImpl utilisateurService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String mainPage(Model model) {
		
		Iterable<Task> task_list = taskRepo.findAll();
		model.addAttribute("task_list", task_list);
		//model.addAttribute("user", utilisateurService.loadCurrentUser());
		
		return "index";
	}
}
