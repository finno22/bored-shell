package at.upstream_mobility.itacademy.bored;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
public class BoredShellCommands {

    @ShellMethod(value = "Get an activity", key = "get")
    public String get() {
        return "Random activity";
    }
    
}