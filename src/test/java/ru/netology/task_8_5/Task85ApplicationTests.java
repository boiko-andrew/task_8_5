package ru.netology.task_8_5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Task85ApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> devApp =
            new GenericContainer<>("devapp:latest")
                    .withExposedPorts(8080);
    @Container
    private final GenericContainer<?> prodApp =
            new GenericContainer<>("prodapp:latest")
                    .withExposedPorts(8081);

    @Test
    void checkDevAppResponse() {
        // arrange
        Integer devAppPort = devApp.getMappedPort(8080);
        String expectedDevAppResponse = "Current profile is dev";

        // act
        ResponseEntity<String> devAppEntity =
                restTemplate.getForEntity("http://localhost:" + devAppPort + "/profile",
                        String.class);
        String actualDevAppResponse = devAppEntity.getBody();

        // assert
        Assertions.assertEquals(expectedDevAppResponse, actualDevAppResponse);
    }

    @Test
    void checkProdAppResponse() {
        // arrange
        Integer prodAppPort = prodApp.getMappedPort(8081);
        String expectedProdAppResponse = "Current profile is production";

        // act
        ResponseEntity<String> prodAppEntity =
                restTemplate.getForEntity("http://localhost:" + prodAppPort + "/profile",
                        String.class);
        String actualProdAppResponse = prodAppEntity.getBody();

        // assert
        Assertions.assertEquals(expectedProdAppResponse, actualProdAppResponse);
    }
}