package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DeviceGroup;
import com.mycompany.myapp.repository.DeviceGroupRepository;
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
 * REST controller for managing DeviceGroup.
 */
@RestController
@RequestMapping("/api")
public class DeviceGroupResource {

    private final Logger log = LoggerFactory.getLogger(DeviceGroupResource.class);
        
    @Inject
    private DeviceGroupRepository deviceGroupRepository;
    
    /**
     * POST  /device-groups : Create a new deviceGroup.
     *
     * @param deviceGroup the deviceGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceGroup, or with status 400 (Bad Request) if the deviceGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/device-groups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceGroup> createDeviceGroup(@Valid @RequestBody DeviceGroup deviceGroup) throws URISyntaxException {
        log.debug("REST request to save DeviceGroup : {}", deviceGroup);
        if (deviceGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("deviceGroup", "idexists", "A new deviceGroup cannot already have an ID")).body(null);
        }
        DeviceGroup result = deviceGroupRepository.save(deviceGroup);
        return ResponseEntity.created(new URI("/api/device-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deviceGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /device-groups : Updates an existing deviceGroup.
     *
     * @param deviceGroup the deviceGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceGroup,
     * or with status 400 (Bad Request) if the deviceGroup is not valid,
     * or with status 500 (Internal Server Error) if the deviceGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/device-groups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceGroup> updateDeviceGroup(@Valid @RequestBody DeviceGroup deviceGroup) throws URISyntaxException {
        log.debug("REST request to update DeviceGroup : {}", deviceGroup);
        if (deviceGroup.getId() == null) {
            return createDeviceGroup(deviceGroup);
        }
        DeviceGroup result = deviceGroupRepository.save(deviceGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deviceGroup", deviceGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /device-groups : get all the deviceGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deviceGroups in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/device-groups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DeviceGroup>> getAllDeviceGroups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DeviceGroups");
        Page<DeviceGroup> page = deviceGroupRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/device-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /device-groups/:id : get the "id" deviceGroup.
     *
     * @param id the id of the deviceGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceGroup, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/device-groups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeviceGroup> getDeviceGroup(@PathVariable Long id) {
        log.debug("REST request to get DeviceGroup : {}", id);
        DeviceGroup deviceGroup = deviceGroupRepository.findOne(id);
        return Optional.ofNullable(deviceGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /device-groups/:id : delete the "id" deviceGroup.
     *
     * @param id the id of the deviceGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/device-groups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeviceGroup(@PathVariable Long id) {
        log.debug("REST request to delete DeviceGroup : {}", id);
        deviceGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deviceGroup", id.toString())).build();
    }

}
