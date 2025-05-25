package ucatolica.edu.co.audichek.aplication.usecases.person.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;

import java.util.Map;

@Component
public class PersonStatesFactory {

    private final Map<String, PersonStateStrategy> strategies;

    @Autowired
    public PersonStatesFactory(Map<String, PersonStateStrategy> strategies) {
        this.strategies = strategies;
    }

    public PersonStateStrategy getStrategy(String nameState) {
        PersonStateStrategy strategy = strategies.get(nameState);
        if (strategy == null) {
            throw new BadRequestException("There are no strategy for: " + nameState);
        }
        return strategy;
    }

}
