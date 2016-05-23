package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DeviceGroup.
 */
@Entity
@Table(name = "device_group")
public class DeviceGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @NotNull
    @Size(min = 4, max = 20)
    @Column(name = "group_name", length = 20, nullable = false)
    private String groupName;

    @Column(name = "group_description")
    private String groupDescription;

    @OneToMany(mappedBy = "deviceGroup")
    @JsonIgnore
    private Set<Device> devices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceGroup deviceGroup = (DeviceGroup) o;
        if(deviceGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, deviceGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeviceGroup{" +
            "id=" + id +
            ", groupId='" + groupId + "'" +
            ", groupName='" + groupName + "'" +
            ", groupDescription='" + groupDescription + "'" +
            '}';
    }
}
