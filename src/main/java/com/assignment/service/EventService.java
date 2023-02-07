package com.assignment.service;

import com.assignment.exception.EventNotFoundException;
import com.assignment.dto.EventRequest;
import com.assignment.entity.Event;
import com.assignment.exception.InvalidInputException;
import com.assignment.repo.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;

    public Event saveEvent(@RequestBody EventRequest eventRequest) {
        Event event = mapToEvent(eventRequest);
        return eventRepo.save(event);
    }

    public Event getEvent(int id) throws EventNotFoundException {
        Optional<Event> event = eventRepo.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new EventNotFoundException("Event entity not found with id : " + id);
        }
    }

    public List<Event> getEvents(int limit, String input) throws EventNotFoundException, InvalidInputException {
        if (!validInput(input)) {
            throw new InvalidInputException("Invalid input provided");
        }
        List<Event> all = filterQueryByInput(input, limit).get();
        if (!all.isEmpty()) {
            return all;
        } else {
            throw new EventNotFoundException("Event entity not found with input : " + input);
        }
    }

    public Long getSumByInput(String input) throws EventNotFoundException, InvalidInputException {
        if (!validInput(input)) {
            throw new InvalidInputException("Invalid input provided");
        }
        return input.equals("cost") ? eventRepo.sumCost()
                : eventRepo.sumDuration();
    }

    public Optional<List<Event>> filterQueryByInput(String input, int limit) {
        Page<Event> page = eventRepo.findAll(PageRequest.of(0, limit, Sort.by(Sort.Order.asc(input))));
        return Optional.of(page.getContent());
    }

    public Event mapToEvent(EventRequest eventRequest) {
        Event event = Event.builder().name(eventRequest.getName()).cost(eventRequest.getCost())
                .duration(eventRequest.getDuration()).location(eventRequest.getLocation()).build();
        return event;
    }

    public boolean validInput(String input) {
        if (input.equals("cost") || input.equals("duration")) {
            return true;
        }
        return false;
    }


}
