package ch.hearc.dfts.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
							@RequestParam(defaultValue="0") Integer page,
							@RequestParam(defaultValue="3") Integer pageSize)
	{
		List<Task> taskList = taskService.getAllTasks(page, pageSize);
		
		int nbTasks = taskList.size();
		int nbPages = ((nbTasks % pageSize) == 0) ? nbTasks/pageSize : nbTasks/pageSize + 1;
		
		List<Integer> pages = IntStream.range(1, nbPages+1).boxed().collect(Collectors.toList());
		
		model.addAttribute("task_list", taskList);
		model.addAttribute("pages", pages);
		//model.addAttribute("user", utilisateurService.loadCurrentUser());
		
		return "index";
	}
}
