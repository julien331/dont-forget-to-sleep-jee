package ch.hearc.dfts.models.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ch.hearc.dfts.models.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
	Page<Task> findByDone(boolean done, Pageable pageable);
	Page<Task> findByUsers_Name(String userName, Pageable pageable);
	Page<Task> findByUsers_NameAndDone(String userName, boolean done, Pageable pageable);

	Page<Task> findByNameLikeIgnoreCase(String name, Pageable pageable);
	Page<Task> findByUsers_NameAndNameLikeIgnoreCase(String userName, String name, Pageable pageable);
}
