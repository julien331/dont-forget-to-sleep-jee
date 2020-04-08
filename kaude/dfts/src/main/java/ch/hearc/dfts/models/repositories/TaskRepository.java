package ch.hearc.dfts.models.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ch.hearc.dfts.models.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

}
