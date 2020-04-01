package ch.hearc.dfts.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.repositories.TaskRepository;
import ch.hearc.dfts.models.services.UserDetailServiceImpl;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskRepository taskRepo;
	@Autowired
	private UserDetailServiceImpl utilisateurService;
	
	@RequestMapping(value="/edit/{id}/done",method=RequestMethod.GET)
	public String mark_done(Model model, @PathVariable long id) {
		
		Optional<Task> task = taskRepo.findById(id);

		if(task != null)
		{
			Task t = task.get();
			t.setDone(true);
			taskRepo.save(t);
		}
		
		return "redirect:/";
	}
}
