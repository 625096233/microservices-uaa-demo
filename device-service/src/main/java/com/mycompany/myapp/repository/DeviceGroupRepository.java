package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DeviceGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DeviceGroup entity.
 */
@SuppressWarnings("unused")
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup,Long> {

}
