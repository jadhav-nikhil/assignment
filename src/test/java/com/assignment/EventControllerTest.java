package com.assignment;

import com.assignment.controller.EventController;
import com.assignment.dto.EventRequest;
import com.assignment.entity.Event;
import com.assignment.exception.EventNotFoundException;
import com.assignment.exception.InvalidInputException;
import com.assignment.repo.EventRepo;
import com.assignment.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @InjectMocks
    EventController EventController;
    @Mock
    EventService EventService;

    @Test
    public void testAddEvent()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Event event = getEvent();
        EventRequest eventRequest = getEventRequest();
        when(EventService.saveEvent(any(EventRequest.class))).thenReturn(event);
        ResponseEntity<Event> responseEntity = EventController.saveEvent(new EventRequest("Nikhil","pune",12,10));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);

    }

    @Test
    public void testGetByIDEvent() throws EventNotFoundException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Event event = getEvent();
        EventRequest eventRequest = getEventRequest();
        when(EventService.getEvent(any(Integer.class))).thenReturn(event);
        ResponseEntity<Event> responseEntity = EventController.getEvent(1);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void validateSumByInput() throws EventNotFoundException, InvalidInputException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Event event = getEvent();
        EventRequest eventRequest = getEventRequest();
        when(EventService.getSumByInput(any(String.class))).thenReturn(36);
        ResponseEntity<Integer> responseEntity = EventController.getEvents("cost");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
    
    private EventRequest getEventRequest(){
       return new EventRequest("Nikhil","pune",12,10);
    }

    private Event getEvent(){
        return Event.builder().id(1).cost(10).duration(12).name("Nikhil").location("pune").build();
    }

}
