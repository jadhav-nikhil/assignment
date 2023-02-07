package com.assignment.repo;

import com.assignment.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepo extends JpaRepository<Event,Integer> {

    Page<Event> findAll(Pageable pageable);
    @Query("SELECT SUM(e.duration) FROM Event e")
    Long sumDuration();
    @Query("SELECT SUM(e.cost) FROM Event e")
    Long sumCost();


}
