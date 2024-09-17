package at.upstream_mobility.itacademy.bored;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

@Component
public class ActivityTypeValueProvider implements ValueProvider {

    @Override
    public List<CompletionProposal> complete(CompletionContext completionContext) {
        return Activity.VALID_TYPES.stream()
            .map(CompletionProposal::new)
            .collect(Collectors.toList());
    }
}
