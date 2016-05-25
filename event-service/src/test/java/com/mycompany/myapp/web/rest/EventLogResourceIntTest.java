package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.EventApp;
import com.mycompany.myapp.domain.EventLog;
import com.mycompany.myapp.repository.EventLogRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.EventType;

/**
 * Test class for the EventLogResource REST controller.
 *
 * @see EventLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventApp.class)
@WebAppConfiguration
@IntegrationTest
public class EventLogResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final Long DEFAULT_EVENT_ID = 1L;
    private static final Long UPDATED_EVENT_ID = 2L;

    private static final EventType DEFAULT_EVENT_TYPE = EventType.ALERT;
    private static final EventType UPDATED_EVENT_TYPE = EventType.NOTIFICATION;

    private static final Long DEFAULT_DEVICE_ID = 1L;
    private static final Long UPDATED_DEVICE_ID = 2L;
    private static final String DEFAULT_EVENT_DATA = "AAAAA";
    private static final String UPDATED_EVENT_DATA = "BBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_EVENT_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_EVENT_DATE_TIME_STR = dateTimeFormatter.format(DEFAULT_EVENT_DATE_TIME);

    @Inject
    private EventLogRepository eventLogRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEventLogMockMvc;

    private EventLog eventLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventLogResource eventLogResource = new EventLogResource();
        ReflectionTestUtils.setField(eventLogResource, "eventLogRepository", eventLogRepository);
        this.restEventLogMockMvc = MockMvcBuilders.standaloneSetup(eventLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        eventLog = new EventLog();
        eventLog.setEventId(DEFAULT_EVENT_ID);
        eventLog.setEventType(DEFAULT_EVENT_TYPE);
        eventLog.setDeviceId(DEFAULT_DEVICE_ID);
        eventLog.setEventData(DEFAULT_EVENT_DATA);
        eventLog.setEventDateTime(DEFAULT_EVENT_DATE_TIME);
    }

    @Test
    @Transactional
    public void createEventLog() throws Exception {
        int databaseSizeBeforeCreate = eventLogRepository.findAll().size();

        // Create the EventLog

        restEventLogMockMvc.perform(post("/api/event-logs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventLog)))
                .andExpect(status().isCreated());

        // Validate the EventLog in the database
        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertThat(eventLogs).hasSize(databaseSizeBeforeCreate + 1);
        EventLog testEventLog = eventLogs.get(eventLogs.size() - 1);
        assertThat(testEventLog.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testEventLog.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testEventLog.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testEventLog.getEventData()).isEqualTo(DEFAULT_EVENT_DATA);
        assertThat(testEventLog.getEventDateTime()).isEqualTo(DEFAULT_EVENT_DATE_TIME);
    }

    @Test
    @Transactional
    public void checkEventTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLogRepository.findAll().size();
        // set the field null
        eventLog.setEventType(null);

        // Create the EventLog, which fails.

        restEventLogMockMvc.perform(post("/api/event-logs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventLog)))
                .andExpect(status().isBadRequest());

        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertThat(eventLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLogRepository.findAll().size();
        // set the field null
        eventLog.setDeviceId(null);

        // Create the EventLog, which fails.

        restEventLogMockMvc.perform(post("/api/event-logs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventLog)))
                .andExpect(status().isBadRequest());

        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertThat(eventLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEventDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLogRepository.findAll().size();
        // set the field null
        eventLog.setEventDateTime(null);

        // Create the EventLog, which fails.

        restEventLogMockMvc.perform(post("/api/event-logs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventLog)))
                .andExpect(status().isBadRequest());

        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertThat(eventLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventLogs() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLog);

        // Get all the eventLogs
        restEventLogMockMvc.perform(get("/api/event-logs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(eventLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID.intValue())))
                .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.intValue())))
                .andExpect(jsonPath("$.[*].eventData").value(hasItem(DEFAULT_EVENT_DATA.toString())))
                .andExpect(jsonPath("$.[*].eventDateTime").value(hasItem(DEFAULT_EVENT_DATE_TIME_STR)));
    }

    @Test
    @Transactional
    public void getEventLog() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLog);

        // Get the eventLog
        restEventLogMockMvc.perform(get("/api/event-logs/{id}", eventLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(eventLog.getId().intValue()))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID.intValue()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.intValue()))
            .andExpect(jsonPath("$.eventData").value(DEFAULT_EVENT_DATA.toString()))
            .andExpect(jsonPath("$.eventDateTime").value(DEFAULT_EVENT_DATE_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEventLog() throws Exception {
        // Get the eventLog
        restEventLogMockMvc.perform(get("/api/event-logs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventLog() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLog);
        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();

        // Update the eventLog
        EventLog updatedEventLog = new EventLog();
        updatedEventLog.setId(eventLog.getId());
        updatedEventLog.setEventId(UPDATED_EVENT_ID);
        updatedEventLog.setEventType(UPDATED_EVENT_TYPE);
        updatedEventLog.setDeviceId(UPDATED_DEVICE_ID);
        updatedEventLog.setEventData(UPDATED_EVENT_DATA);
        updatedEventLog.setEventDateTime(UPDATED_EVENT_DATE_TIME);

        restEventLogMockMvc.perform(put("/api/event-logs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEventLog)))
                .andExpect(status().isOk());

        // Validate the EventLog in the database
        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertThat(eventLogs).hasSize(databaseSizeBeforeUpdate);
        EventLog testEventLog = eventLogs.get(eventLogs.size() - 1);
        assertThat(testEventLog.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testEventLog.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testEventLog.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testEventLog.getEventData()).isEqualTo(UPDATED_EVENT_DATA);
        assertThat(testEventLog.getEventDateTime()).isEqualTo(UPDATED_EVENT_DATE_TIME);
    }

    @Test
    @Transactional
    public void deleteEventLog() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLog);
        int databaseSizeBeforeDelete = eventLogRepository.findAll().size();

        // Get the eventLog
        restEventLogMockMvc.perform(delete("/api/event-logs/{id}", eventLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EventLog> eventLogs = eventLogRepository.findAll();
        assertThat(eventLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
