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
     
    public List<Task> getAllTasks(Integer page, Integer pageSize, Long userId) {
        Pageable paging = PageRequest.of(page, pageSize);

    	Page<Task> pagedResult = null;
 
        Optional<User> u = userRepo.findById(userId);
        if(u.isPresent())
        {
        	User user = u.get();
        	
        	
        	if(user.getName().equals("admin"))
        		pagedResult = taskRepo.findAll(paging);
        	else
        		pagedResult = taskRepo.findByUsers(userId, paging);
        		
        }
         
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
}
