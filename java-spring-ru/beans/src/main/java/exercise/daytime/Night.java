package exercise.daytime;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
@Qualifier
public class Night implements Daytime {
    private static final String NAME = "night";

    public String getName() {
        return NAME;
    }

    // BEGIN
    @PostConstruct
    public static void init() {
        log.info("Bean is initialized!");
    }

    @PreDestroy
    public void cleanup() {
        log.info("Cleaning up resources or performing final actions!");
    }
    // END
}
