package ch.hearc.dfts.controller;

import java.util.Optional;

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

		if(task.isPresent())
		{
			Task t = task.get();
			t.setDone(true);
			taskRepo.save(t);
		}
		
		return "redirect:/";
	}
	@GetMapping("/tasks")
	public String getTestData(Model model) {

		Iterable<Task> tasks = taskRepo.findAll();

		model.addAttribute("tasks", tasks);

		return "index";
	}
	
	@GetMapping("")
	public String addTask(Model model) {
		Task task = new Task();
	    model.addAttribute("task", task);
		return "form";
	}
	
	@PostMapping("")
	public String addTask(@Valid Task task, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "form.html";
		}

		taskRepo.save(task);
		model.addAttribute("tasks", taskRepo.findAll());

		return "index.html";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTask(@Valid Task task, BindingResult result, Model model, @PathVariable Long id) {
		if (result.hasErrors()) {
			return "index.html";
		}

		taskRepo.delete(task);
		model.addAttribute("tasks", taskRepo.findAll());

		return "index.html";
	}
}
