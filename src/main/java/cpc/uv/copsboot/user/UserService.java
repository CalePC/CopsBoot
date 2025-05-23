package cpc.uv.copsboot.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findUserByAuthServerId(AuthServerId authServerId) {
        return repository.findByAuthServerId(authServerId);
    }
    public User createUser(CreateUserParameters createUserParameters) {
        UserId userId = repository.nextId();
        User user = new User(
                userId,
                createUserParameters.email(),
                createUserParameters.authServerId(),
                createUserParameters.mobileToken()
        );
        return repository.save(user);
    }

}
