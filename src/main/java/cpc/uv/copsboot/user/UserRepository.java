package cpc.uv.copsboot.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UserId>, UserRepositoryCustom {
}
