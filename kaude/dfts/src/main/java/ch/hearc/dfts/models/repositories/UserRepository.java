package ch.hearc.dfts.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ch.hearc.dfts.models.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByName(String name);
}
