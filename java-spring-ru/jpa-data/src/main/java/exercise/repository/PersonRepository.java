package exercise.repository;

import exercise.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

// BEGIN
public interface PersonRepository extends JpaRepository<Person, Long> {
}
// END
