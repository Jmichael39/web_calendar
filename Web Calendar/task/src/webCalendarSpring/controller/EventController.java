package webCalendarSpring.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import webCalendarSpring.entity.Event;
import webCalendarSpring.service.EventService;

import java.time.LocalDate;
import java.util.*;

@RestController
@Validated
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public ResponseEntity<List<Event>> getEvents(@RequestParam(value = "start_time", required = false) LocalDate startDate,
                                                 @RequestParam(value = "end_time", required = false) LocalDate endDate) {

        if (startDate != null && endDate != null) {
            List<Event> eventListByDate = eventService.findByDateRange(startDate, endDate);

            if (eventListByDate.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(eventListByDate, HttpStatus.OK);
        } else {
            List<Event> eventList = eventService.findAll();

            if (eventList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(eventList, HttpStatus.OK);
        }

    }

    @GetMapping("/event/today")
    public ResponseEntity<List<Event>> getTodayEvents() {
        List<Event> eventList = eventService.findByDate(LocalDate.now());

        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Long id) {
        Event event = eventService.findById(id);

        if (event == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The event doesn't exist!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<?> postEvent(@Valid @RequestBody Event event) {
        eventService.saveEvent(event);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "The event has been added!");
        response.put("event", event.getEvent());
        response.put("date", event.getDate());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        Map<String, Object> response = new LinkedHashMap<>();

        if (event == null) {
            response.put("message", "The event doesn't exist!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            eventService.deleteById(id);
            response.put("id", event.getId());
            response.put("event", event.getEvent());
            response.put("date", event.getDate());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleValidationExceptions() {

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
