package ch.hearc.dfts.models.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ch.hearc.dfts.models.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
	Page<Task> findByUsers(Long userId, Pageable pageable);
}
