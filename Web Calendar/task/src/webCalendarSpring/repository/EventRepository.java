package webCalendarSpring.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import webCalendarSpring.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDate(@NotNull LocalDate date);
    List<Event> findByDateBetween(@NotNull LocalDate start, @NotNull LocalDate end);
}
