package at.upstream_mobility.itacademy.bored.integration;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.autoconfigure.ShellTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import at.upstream_mobility.itacademy.bored.Activity;
import at.upstream_mobility.itacademy.bored.BoredApiClient;
import at.upstream_mobility.itacademy.bored.exception.InvalidActivityTypeException;

import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootApplication
@ShellTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan("at.upstream_mobility.itacademy.bored")
class BoredCommandsIntegrationTests {

	@Autowired
    private ShellTestClient client;

	@MockBean
    private BoredApiClient boredApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	void getRandomActivity_shouldReturnRandomActivity() throws InvalidActivityTypeException {

		assertNotNull(client, "ShellTestClient should not be null");

		Activity activityMock = new Activity();
		activityMock.setActivity("Returning a random activity");

		when(boredApiClient.retrieveRandomActivity(null)).thenReturn(activityMock);

		ShellTestClient.NonInteractiveShellSession session = client
			.nonInterative("get")
			.run();

		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen())
				.containsText("Returning a random activity");
		});
	}

    @Test
	void getRandomActivityWithType_shouldReturnRandomActivityWithType_WhenTypeExists() throws InvalidActivityTypeException {

		assertNotNull(client, "ShellTestClient should not be null");

        String validType = "education";
		Activity activityMock = new Activity();
		activityMock.setActivity("Returning a random activity with a type");
        activityMock.setType(validType);

		when(boredApiClient.retrieveRandomActivity(validType)).thenReturn(activityMock);

		ShellTestClient.NonInteractiveShellSession session = client
			.nonInterative("get", validType)
			.run();

		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen())
				.containsText("Returning a random activity with a type");
		});
	}
}