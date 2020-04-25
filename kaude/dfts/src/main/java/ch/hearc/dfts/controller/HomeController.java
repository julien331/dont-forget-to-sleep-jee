package ch.hearc.dfts.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.repositories.UserRepository;
import ch.hearc.dfts.models.services.TaskService;


@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public String mainPage(
							Model model,
							@RequestParam(defaultValue="0") Integer page,
							@RequestParam(defaultValue="3") Integer pageSize,
							@RequestParam(defaultValue="false") Boolean showFinished,
							@RequestParam(defaultValue="") String taskName,
							Principal principal)
	{
		String userName = principal.getName();
		User user = userRepo.findByName(userName);
			
		Page<Task> pagedResult = null;
		
		if(taskName.equals(""))
			pagedResult = taskService.getAllTasks(page, pageSize, user, showFinished);
		else
			pagedResult = taskService.findTask(taskName, pageSize);

		//Default values
		List<Task> taskList = null;
		int nbPages = 1;
		int currentPage = 1;
		boolean isFirstPage = true;
		boolean isLastPage = true;
		
		if(pagedResult != null && pagedResult.hasContent()) {
			taskList = pagedResult.getContent();
			nbPages = pagedResult.getTotalPages();
			currentPage = pagedResult.getNumber();
			isFirstPage = pagedResult.isFirst();
			isLastPage = pagedResult.isLast();
		}
		else
			taskList = new ArrayList<Task>();
		
		model.addAttribute("task_list", taskList);
		model.addAttribute("nb_pages", nbPages);
		model.addAttribute("show_finished", showFinished);
		model.addAttribute("user_name", userName);
		model.addAttribute("current_page", currentPage);
		model.addAttribute("is_first_page", isFirstPage);
		model.addAttribute("is_last_page", isLastPage);
		model.addAttribute("is_admin", user.isAdmin());
		
		return "index";
	}
}
