package cpc.uv.copsboot;

import cpc.uv.orm.jpa.InMemoryUniqueIdGenerator;
import cpc.uv.orm.jpa.UniqueIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class CopsbootApplicationConfiguration {
    @Bean
    public UniqueIdGenerator<UUID> uniqueIdGenerator() {
        return new InMemoryUniqueIdGenerator();
    }
}
