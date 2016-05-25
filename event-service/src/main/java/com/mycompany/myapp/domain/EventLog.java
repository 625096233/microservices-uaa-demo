package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.EventType;

/**
 * A EventLog.
 */
@Entity
@Table(name = "event_log")
public class EventLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @NotNull
    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Column(name = "event_data")
    private String eventData;

    @NotNull
    @Column(name = "event_date_time", nullable = false)
    private ZonedDateTime eventDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public ZonedDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(ZonedDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventLog eventLog = (EventLog) o;
        if(eventLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eventLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EventLog{" +
            "id=" + id +
            ", eventId='" + eventId + "'" +
            ", eventType='" + eventType + "'" +
            ", deviceId='" + deviceId + "'" +
            ", eventData='" + eventData + "'" +
            ", eventDateTime='" + eventDateTime + "'" +
            '}';
    }
}
