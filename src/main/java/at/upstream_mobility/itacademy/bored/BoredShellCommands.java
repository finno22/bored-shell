package at.upstream_mobility.itacademy.bored;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import at.upstream_mobility.itacademy.bored.exception.InvalidActivityTypeException;


@ShellComponent
public class BoredShellCommands {

    private BoredApiClient boredApiClient;

    public BoredShellCommands(BoredApiClient boredApiClient){
        this.boredApiClient = boredApiClient;
    }

    @ShellMethod(value = "Get an activity", key = "get")
    public String get(@ShellOption(defaultValue = ShellOption.NULL, valueProvider = ActivityTypeValueProvider.class) String type) {
        try{
            Activity activity = boredApiClient.retrieveRandomActivity(type);
            return activity.get();
        } catch(InvalidActivityTypeException invalidActivityTypeException){
            return invalidActivityTypeException.getMessage();
        }
    }
    
}