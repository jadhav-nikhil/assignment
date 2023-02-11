package com.assignment;

import com.assignment.controller.EventController;
import com.assignment.dto.EventRequest;
import com.assignment.entity.Event;
import com.assignment.exception.EventNotFoundException;
import com.assignment.exception.InvalidInputException;
import com.assignment.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Test
    void saveEventTest() {
        EventRequest eventRequest = new EventRequest();
        Event event = new Event();
        when(eventService.saveEvent(eventRequest)).thenReturn(event);

        ResponseEntity<Event> response = eventController.saveEvent(eventRequest);

        assertEquals(event, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(eventService).saveEvent(eventRequest);
    }

    @Test
    void getEventTest() throws EventNotFoundException {
        int id = 1;
        Event event = new Event();
        when(eventService.getEvent(id)).thenReturn(event);

        ResponseEntity<Event> response = eventController.getEvent(id);

        assertEquals(event, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService).getEvent(id);
    }

    @Test
    void getEventTest_EventNotFoundException() throws EventNotFoundException {
        int id = 1;
        when(eventService.getEvent(id)).thenThrow(EventNotFoundException.class);

        assertThrows(EventNotFoundException.class, () -> eventController.getEvent(id));
        verify(eventService).getEvent(id);
    }

    @Test
    void getEventsTest() throws EventNotFoundException, InvalidInputException {
        int top = 2;
        String by = "name";
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventService.getEvents(top, by)).thenReturn(events);

        ResponseEntity<List<Event>> response = eventController.getEvents(top, by);

        assertEquals(events, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventService).getEvents(top, by);
    }

    @Test
    void testGetEvents() throws InvalidInputException, EventNotFoundException {
        String by = "name";
        when(eventService.getSumByInput(by)).thenReturn(100l);
        ResponseEntity<Long> events = eventController.getEvents(by);
        assertEquals(HttpStatus.OK, events.getStatusCode());
        assertEquals(100l, events.getBody());
    }

    @Test
    void testGetEventsByInvalidInput() throws InvalidInputException, EventNotFoundException {
        String by = "name";
        when(eventService.getSumByInput(by)).thenThrow(InvalidInputException.class);
        assertThrows(InvalidInputException.class, () -> eventController.getEvents(by));
    }
}