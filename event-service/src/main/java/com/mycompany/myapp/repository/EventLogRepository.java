package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EventLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventLog entity.
 */
@SuppressWarnings("unused")
public interface EventLogRepository extends JpaRepository<EventLog,Long> {

}
