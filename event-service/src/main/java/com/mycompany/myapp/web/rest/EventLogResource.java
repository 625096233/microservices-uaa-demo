package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.EventLog;
import com.mycompany.myapp.repository.EventLogRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EventLog.
 */
@RestController
@RequestMapping("/api")
public class EventLogResource {

    private final Logger log = LoggerFactory.getLogger(EventLogResource.class);
        
    @Inject
    private EventLogRepository eventLogRepository;
    
    /**
     * POST  /event-logs : Create a new eventLog.
     *
     * @param eventLog the eventLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventLog, or with status 400 (Bad Request) if the eventLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/event-logs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventLog> createEventLog(@Valid @RequestBody EventLog eventLog) throws URISyntaxException {
        log.debug("REST request to save EventLog : {}", eventLog);
        if (eventLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("eventLog", "idexists", "A new eventLog cannot already have an ID")).body(null);
        }
        EventLog result = eventLogRepository.save(eventLog);
        return ResponseEntity.created(new URI("/api/event-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eventLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-logs : Updates an existing eventLog.
     *
     * @param eventLog the eventLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventLog,
     * or with status 400 (Bad Request) if the eventLog is not valid,
     * or with status 500 (Internal Server Error) if the eventLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/event-logs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventLog> updateEventLog(@Valid @RequestBody EventLog eventLog) throws URISyntaxException {
        log.debug("REST request to update EventLog : {}", eventLog);
        if (eventLog.getId() == null) {
            return createEventLog(eventLog);
        }
        EventLog result = eventLogRepository.save(eventLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eventLog", eventLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-logs : get all the eventLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventLogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/event-logs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EventLog>> getAllEventLogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EventLogs");
        Page<EventLog> page = eventLogRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/event-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /event-logs/:id : get the "id" eventLog.
     *
     * @param id the id of the eventLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventLog, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/event-logs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EventLog> getEventLog(@PathVariable Long id) {
        log.debug("REST request to get EventLog : {}", id);
        EventLog eventLog = eventLogRepository.findOne(id);
        return Optional.ofNullable(eventLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /event-logs/:id : delete the "id" eventLog.
     *
     * @param id the id of the eventLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/event-logs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEventLog(@PathVariable Long id) {
        log.debug("REST request to delete EventLog : {}", id);
        eventLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eventLog", id.toString())).build();
    }

}
