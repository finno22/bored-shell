package at.upstream_mobility.itacademy.bored;

import org.springframework.web.reactive.function.client.WebClient;

import at.upstream_mobility.itacademy.bored.exception.InvalidActivityTypeException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class BoredApiClient {
    WebClient client = WebClient.create("https://bored.api.lewagon.com/api");

    public String buildURI(String type) throws InvalidActivityTypeException{
        String uri = "/activity";
        if(type != null){
            if(!Activity.VALID_TYPES.contains(type)){
                throw new InvalidActivityTypeException("ERROR: Invalid activity type " + type + " was provdided.");
            }
            uri += "?type=" + type;
        }
        return uri;
    }
    
    public Activity retrieveRandomActivity(String type) throws InvalidActivityTypeException{ //TODO handle endpoint not available
        String uri = buildURI(type);
        return this.retrieve(uri);
    }

    public Activity retrieve(String uri){
        Mono<Activity> result = client.get()
		.uri(uri).accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(Activity.class);

        return result.block();
    }

}