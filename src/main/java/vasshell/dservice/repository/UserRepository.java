package vasshell.dservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vasshell.dservice.entity.User;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>, JpaSpecificationExecutor<User> {
}
