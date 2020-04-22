package ch.hearc.dfts.controller;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.repositories.TaskRepository;
import ch.hearc.dfts.models.repositories.UserRepository;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskRepository taskRepo;
	@Autowired
	private UserRepository userRepo;
	public long savedId=0;
	
	private final String HOME_REDIRECTION = "redirect:/";
	
	@RequestMapping(value="/edit/{id}/done",method=RequestMethod.GET)
	public String mark_done(Model model, @PathVariable long id) {
		Optional<Task> task = taskRepo.findById(id);

		if(task.isPresent())
		{
			Task t = task.get();
			t.setDone(true);
			taskRepo.save(t);
		}
		
		return HOME_REDIRECTION;
	}
	
	@GetMapping("{id}")
	public String modify(Model model, @PathVariable long id) {
		Optional<Task> task = taskRepo.findById(id);

		if(task.isPresent())
		{
			Task t = task.get();
			
			model.addAttribute("task", t);
			model.addAttribute("formTitle", "Modification d'une tâche");
			
			savedId=id;
			
			return "form";
		}

		return HOME_REDIRECTION;
	}
	
	@GetMapping("")
	public String addTask(Model model) {
		Task task = new Task();
	    model.addAttribute("task", task);
		model.addAttribute("formTitle", "Ajout d'une tâche");
		return "form";
	}
	
	@PostMapping("")
	public String addTask(@Valid Task task, BindingResult result, Model model, Principal principal) {
		if (result.hasErrors()) {
			return "form";
		}
		
		long id=savedId;
		savedId=0;
		
		//modifying a task
		if(id==0)
		{		
			String userName = principal.getName();
			User u = userRepo.findByName(userName);
			
			taskRepo.save(task);
			
			Set<Task> t = u.getTasks();
			t.add(task);
			userRepo.save(u);
		}
		//adding a task
		else
		{
			Optional<Task> taskToChange = taskRepo.findById(id);
			if(taskToChange.isPresent())
			{
				Task t = taskToChange.get();
				t.setName(task.getName());
				t.setDescription(task.getDescription());
				
				taskRepo.save(t);
			}
		}
		return HOME_REDIRECTION;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTask(@Valid Task task, BindingResult result, Model model, @PathVariable Long id) {
		if (result.hasErrors()) {
			return HOME_REDIRECTION;
		}
		
		Optional<Task> t = taskRepo.findById(id);
		if(t.isPresent())
		{
			Task task2 = t.get();
			
			for(User u : task2.getUsers())
			{
				u.getTasks().remove(task2);
				userRepo.save(u);
			}
			
			taskRepo.delete(task2);
		}

		return HOME_REDIRECTION;
	}
}
