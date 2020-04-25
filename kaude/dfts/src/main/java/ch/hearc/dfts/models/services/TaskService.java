package ch.hearc.dfts.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.repositories.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
    TaskRepository taskRepo;
     
    public Page<Task> getAllTasks(Integer page, Integer pageSize, User user, boolean showDone) {
        Pageable paging = PageRequest.of(page, pageSize);

    	Page<Task> pagedResult = null;
        
        if(user.isAdmin()) {
        	if(showDone)
        		pagedResult = taskRepo.findAll(paging);
        	else
        		pagedResult = taskRepo.findByDone(false, paging);
        }else {
        	if(showDone)
        		pagedResult = taskRepo.findByUsers_Name(user.getName(), paging);
        	else
        		pagedResult = taskRepo.findByUsers_NameAndDone(user.getName(), false, paging);
        }
        
        return pagedResult;
    }
    
    public Page<Task> findTask(String taskName, Integer pageSize, User user) {
    	Pageable paging = PageRequest.of(0, pageSize);
    	
    	Page<Task> pagedResult = null;
    	
    	if(user.isAdmin()) 
    		pagedResult = taskRepo.findByNameLikeIgnoreCase("%"+taskName+"%", paging);
    	else
    		pagedResult = taskRepo.findByUsers_NameAndNameLikeIgnoreCase(user.getName(), "%"+taskName+"%", paging);
        
    	return pagedResult;
    }
}
