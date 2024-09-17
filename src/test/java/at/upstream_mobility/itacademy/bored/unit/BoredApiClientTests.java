package at.upstream_mobility.itacademy.bored.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import at.upstream_mobility.itacademy.bored.Activity;
import at.upstream_mobility.itacademy.bored.BoredApiClient;
import at.upstream_mobility.itacademy.bored.exception.InvalidActivityTypeException;
import reactor.core.publisher.Mono;

public class BoredApiClientTests {
    @InjectMocks
    private BoredApiClient boredApiClient;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getValidActivityTypes_shouldReturnListWithNineActivityTypes() {
        Set<String> validActivityTypes = Activity.VALID_TYPES;
        int NUM_VALID_ACTIVITY_TYPES = 9;
        assertEquals(NUM_VALID_ACTIVITY_TYPES, validActivityTypes.size());
        assertTrue(validActivityTypes.contains("education"));
        assertTrue(validActivityTypes.contains("recreational"));
    }

    @Test
    void buildURIWithoutType_shouldReturnBasicURI(){
        String uri = assertDoesNotThrow(() -> boredApiClient.buildURI(null));
        assertEquals("/activity", uri);
    }

    @Test
    void buildURIWithType_shouldReturnURIContainingType_WhenTypeExists(){
        String validType = "education";
        String uri = assertDoesNotThrow(() -> boredApiClient.buildURI(validType));
        assertEquals("/activity?type=" + validType, uri);
    }

    @Test
    void buildURIWithType_shouldThrowInvalidTypeException_WhenTypeDoesNotExist(){
        String invalidType = "invalidType";

        InvalidActivityTypeException exception = assertThrows(InvalidActivityTypeException.class,
                () -> boredApiClient.buildURI(invalidType));
        
        assertEquals("ERROR: Invalid activity type invalidType was provdided.", exception.getMessage());

    }

    @Test
    void getActivityWithoutType_shouldRetrieveRandomActivity() {
        Activity activityMock = new Activity();
        activityMock.setActivity("Do something random");
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/activity")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Activity.class)).thenReturn(Mono.just(activityMock));
        
        Activity actualActivity = assertDoesNotThrow(() -> boredApiClient.retrieveRandomActivity(null));

        assertEquals("Do something random", actualActivity.get());
    }

    @Test
    void getActivityWithType_shouldRetrieveRandomActivityWithType_whenTypeExists() {
        String validType = "education";
        Activity activityMock = new Activity();
        activityMock.setActivity("Learn something new");
        activityMock.setType(validType);
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/activity?type=" + validType)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Activity.class)).thenReturn(Mono.just(activityMock));

        Activity actualActivity = assertDoesNotThrow(() -> boredApiClient.retrieveRandomActivity(validType));

        assertEquals("Learn something new", actualActivity.get());
        assertEquals("education", actualActivity.getType());
    }

    @Test
    void getActivityWithType_shouldThrowInvalidActivityTypeException_whenTypeDoesNotExist() {
        String invalidType = "invalidType";
        InvalidActivityTypeException exception = assertThrows(InvalidActivityTypeException.class,
                () -> boredApiClient.retrieveRandomActivity(invalidType));

        assertEquals("ERROR: Invalid activity type invalidType was provdided.", exception.getMessage());
    }
}
