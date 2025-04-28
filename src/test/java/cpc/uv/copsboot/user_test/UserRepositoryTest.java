package cpc.uv.copsboot.user_test;

import cpc.uv.copsboot.user.AuthServerId;
import cpc.uv.copsboot.user.User;
import cpc.uv.copsboot.user.UserId;
import cpc.uv.copsboot.user.UserRepository;
import cpc.uv.orm.jpa.InMemoryUniqueIdGenerator;
import cpc.uv.orm.jpa.UniqueIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserRepositoryTest.TestConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testStoreUser() {

        UserId userId = repository.nextId();

        User user = repository.save(
                new User(
                        userId,
                        "officer@example.com",
                        new AuthServerId(UUID.randomUUID()),
                        "some-mobile-token-value")
        );

        assertThat(user).isNotNull();
        assertThat(repository.count()).isEqualTo(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> uniqueIdGenerator() {
            return new InMemoryUniqueIdGenerator();
        }
    }
}
