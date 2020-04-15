package ch.hearc.dfts.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.repositories.TaskRepository;
import ch.hearc.dfts.models.repositories.UserRepository;

@Service
public class TaskService {
	
	@Autowired
    TaskRepository taskRepo;
	
	@Autowired
	UserRepository userRepo;
     
    public Page<Task> getAllTasks(Integer page, Integer pageSize, String userName) {
        Pageable paging = PageRequest.of(page, pageSize);

    	Page<Task> pagedResult = null;
 
        User user = userRepo.findByName(userName);
    	if(user.getName().equals("admin"))
    		pagedResult = taskRepo.findAll(paging);
    	else
    		pagedResult = taskRepo.findByUsers_Name(user.getName(), paging);
         
        return pagedResult;
    }
}
