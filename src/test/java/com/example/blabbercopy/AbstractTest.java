package com.example.blabbercopy;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@Import({TestcontainersConfiguration.class, TestConfiguration.class})
public class AbstractTest {
    static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:7.0.12"))
            .withReuse(true)
            .withExposedPorts(6379);

    protected static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("apache/kafka:3.7.2"))
            .withReuse(true);

    @BeforeAll
    public static void beforeAll() {
        REDIS_CONTAINER.start();
        KAFKA.start();

        System.setProperty("spring.kafka.bootstrap-servers", KAFKA.getBootstrapServers());
        System.setProperty("spring.data.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.data.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
    }

}
