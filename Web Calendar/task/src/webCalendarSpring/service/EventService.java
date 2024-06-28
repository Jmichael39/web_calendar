package webCalendarSpring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webCalendarSpring.entity.Event;
import webCalendarSpring.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

// This is the class responsible for the business logic, here we just implement methods and their return values
@Service
public class EventService {
    private final EventRepository eventRepository;

    // this annotation allows Spring to automatically wire the required beans (dependencies) into your classes, eliminating the need for manual configuration
    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Event> findByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findByDateBetween(startDate, endDate);
    }
}
