package ch.hearc.dfts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.repositories.TaskRepository;
import ch.hearc.dfts.models.services.TaskService;
import ch.hearc.dfts.models.services.UserDetailServiceImpl;


@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private TaskRepository taskRepo;
	@Autowired
	private UserDetailServiceImpl utilisateurService;
	@Autowired
	private TaskService taskService;
	
	@GetMapping
	public String mainPage(
							Model model,
							@RequestParam(defaultValue="0") Integer pageNo,
							@RequestParam(defaultValue="3") Integer pageSize)
	{
//		Iterable<Task> task_list = taskRepo.findAll();
		Iterable<Task> task_list = taskService.getAllTasks(pageNo, pageSize);
		model.addAttribute("task_list", task_list);
		//model.addAttribute("user", utilisateurService.loadCurrentUser());
		
		return "index";
	}
}
