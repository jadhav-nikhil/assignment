package com.assignment;

import com.assignment.dto.EventRequest;
import com.assignment.entity.Event;
import com.assignment.exception.EventNotFoundException;
import com.assignment.exception.InvalidInputException;
import com.assignment.repo.EventRepo;
import com.assignment.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    public EventService eventService;

    @Mock
    public EventRepo eventRepo;

    Event event;

    EventRequest eventRequest;

    @BeforeEach
    public void setUp() {
        event = new Event(0, "Nikhil", "Pune", 10, 300);
        eventRequest = new EventRequest("Nikhil", "Pune", 10, 300);
    }

    @Test
    public void saveTest() {
        when(eventRepo.save(event)).thenReturn(event);
        Event savedEvent = eventService.saveEvent(eventRequest);
        assertEquals(event, savedEvent);
        verify(eventRepo, atMost(1)).save(event);
    }

    @Test
    public void testGetEvent() throws EventNotFoundException {
        when(eventRepo.findById(1)).thenReturn(Optional.of(event));
        Event event1 = eventService.getEvent(1);
        assertNotNull(event1);
        assertEquals(event.getName(), event1.getName());
    }

    @Test
    public void testGetEventNotFound() throws EventNotFoundException {
        when(eventRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundException.class, () -> eventService.getEvent(1));
    }


    @Test
    public void testGetEvents_WhenInputIsValid_ShouldReturnEventList() throws EventNotFoundException, InvalidInputException {
        String input = "duration";
        int limit = 2;
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, "Event 1", "New York", 10, 100));
        events.add(new Event(2, "Event 2", "London", 20, 200));

        when(eventRepo.findAll(PageRequest.of(0, 2, Sort.by(Sort.Order.asc(input)))))
                .thenReturn(new PageImpl<>(events));

        List<Event> result = eventService.getEvents(limit, input);

        assertEquals(events, result);
    }

    @Test
    public void testGetEvents_WhenListIsEmpty_ShouldThrowsException() throws EventNotFoundException, InvalidInputException {
        String input = "duration";
        int limit = 2;
        when(eventRepo.findAll(PageRequest.of(0, 2, Sort.by(Sort.Order.asc(input)))))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        assertThrows(EventNotFoundException.class, () -> eventService.getEvents(limit, input));
    }


    @Test
    public void testGetEventInvalidInput() throws InvalidInputException, EventNotFoundException {
        String input = "Invalid";
        int limit = 2;
        assertThrows(InvalidInputException.class, () -> eventService.getEvents(limit, input));
    }

    @Test
    public void validInput() {
        Boolean result = eventService.validInput("cost");
        assertTrue(result);
    }

    @Test
    public void testValidInput_WhenInputIsInvalid_ShouldReturnFalse() {
        String input = "";
        boolean result = eventService.validInput(input);
        assertFalse(result);
    }

    @Test
    public void testGetSumByInputByValidInput() throws InvalidInputException, EventNotFoundException {
        String input = "cost";
        when(eventRepo.sumCost()).thenReturn(300l);
        Long result = eventService.getSumByInput(input);
        assertEquals(300l,result);
    }

    @Test
    public void testGetSumByInputByValidInput2() throws InvalidInputException, EventNotFoundException {
        String input = "duration";
        when(eventRepo.sumDuration()).thenReturn(400l);
        Long result = eventService.getSumByInput(input);
        assertEquals(400l,result);
    }

    @Test
    public void testGetSumByInputByInvalidInput() {
        String input = "invalid";
        assertThrows(InvalidInputException.class, () -> eventService.getSumByInput(input));
    }

}
