package com.assignment.controller;


import com.assignment.dto.EventRequest;
import com.assignment.entity.Event;
import com.assignment.exception.EventNotFoundException;
import com.assignment.exception.InvalidInputException;
import com.assignment.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/save")
    public ResponseEntity<Event> saveEvent(@RequestBody EventRequest eventRequest){
        return new ResponseEntity<>(eventService.saveEvent(eventRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable int id) throws EventNotFoundException {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @GetMapping("{top}/fetch")
    public ResponseEntity<List<Event>> getEvents(@PathVariable int top,@RequestParam String by) throws EventNotFoundException, InvalidInputException {
        return ResponseEntity.ok(eventService.getEvents(top,by));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getEvents(@RequestParam String by) throws EventNotFoundException, InvalidInputException {
        return ResponseEntity.ok(eventService.getSumByInput(by));
    }
}
