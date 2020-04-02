package ch.hearc.dfts.models.repositories;

import org.springframework.data.repository.CrudRepository;
import ch.hearc.dfts.models.Role;
import ch.hearc.dfts.models.User;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByName(String name);
}
