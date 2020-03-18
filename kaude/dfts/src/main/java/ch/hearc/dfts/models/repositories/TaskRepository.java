package ch.hearc.dfts.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ch.hearc.dfts.models.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
