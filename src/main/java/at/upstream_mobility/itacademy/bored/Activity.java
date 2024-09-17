package at.upstream_mobility.itacademy.bored;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Setter;

@Setter
public class Activity {
    private String activity;
    private String type;

    private static final String[] TYPES = new String[] {"education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork"};
    public static final Set<String> VALID_TYPES = new HashSet<>(Arrays.asList(TYPES));
    
    public String get() {
        return activity;
    }

    public String getType(){
        return type;
    }
}
