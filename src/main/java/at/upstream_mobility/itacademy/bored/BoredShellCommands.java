package at.upstream_mobility.itacademy.bored;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
public class BoredShellCommands {

    private BoredApiClient boredApiClient;

    public BoredShellCommands(BoredApiClient boredApiClient){
        this.boredApiClient = boredApiClient;
    }

    @ShellMethod(value = "Get an activity", key = "get")
    public String get() {
        return boredApiClient.retrieveRandomActivity().get();
    }
    
}