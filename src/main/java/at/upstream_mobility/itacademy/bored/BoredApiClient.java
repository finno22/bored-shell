package at.upstream_mobility.itacademy.bored;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class BoredApiClient {
    WebClient client = WebClient.create("https://bored.api.lewagon.com/api");

    public Activity retrieveRandomActivity(){ //TODO handle endpoint not available
        String uri = "/activity";
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