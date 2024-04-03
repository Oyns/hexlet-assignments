package exercise.daytime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

// Интерфейс содержит метод, возвращающий название времени суток
// Реализация методов представлена в классах Morning, Day, Evening, Night,
// которые реализуют этот интерфейс
@Component
@RequestScope
@ConditionalOnExpression
public interface Daytime {

    String getName();
}
