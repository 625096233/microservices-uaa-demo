package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DeviceApp;
import com.mycompany.myapp.domain.DeviceGroup;
import com.mycompany.myapp.repository.DeviceGroupRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DeviceGroupResource REST controller.
 *
 * @see DeviceGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DeviceApp.class)
@WebAppConfiguration
@IntegrationTest
public class DeviceGroupResourceIntTest {


    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;
    private static final String DEFAULT_GROUP_NAME = "AAAA";
    private static final String UPDATED_GROUP_NAME = "BBBB";
    private static final String DEFAULT_GROUP_DESCRIPTION = "AAAAA";
    private static final String UPDATED_GROUP_DESCRIPTION = "BBBBB";

    @Inject
    private DeviceGroupRepository deviceGroupRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeviceGroupMockMvc;

    private DeviceGroup deviceGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceGroupResource deviceGroupResource = new DeviceGroupResource();
        ReflectionTestUtils.setField(deviceGroupResource, "deviceGroupRepository", deviceGroupRepository);
        this.restDeviceGroupMockMvc = MockMvcBuilders.standaloneSetup(deviceGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        deviceGroup = new DeviceGroup();
        deviceGroup.setGroupId(DEFAULT_GROUP_ID);
        deviceGroup.setGroupName(DEFAULT_GROUP_NAME);
        deviceGroup.setGroupDescription(DEFAULT_GROUP_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDeviceGroup() throws Exception {
        int databaseSizeBeforeCreate = deviceGroupRepository.findAll().size();

        // Create the DeviceGroup

        restDeviceGroupMockMvc.perform(post("/api/device-groups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deviceGroup)))
                .andExpect(status().isCreated());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroups = deviceGroupRepository.findAll();
        assertThat(deviceGroups).hasSize(databaseSizeBeforeCreate + 1);
        DeviceGroup testDeviceGroup = deviceGroups.get(deviceGroups.size() - 1);
        assertThat(testDeviceGroup.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testDeviceGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testDeviceGroup.getGroupDescription()).isEqualTo(DEFAULT_GROUP_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceGroupRepository.findAll().size();
        // set the field null
        deviceGroup.setGroupId(null);

        // Create the DeviceGroup, which fails.

        restDeviceGroupMockMvc.perform(post("/api/device-groups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deviceGroup)))
                .andExpect(status().isBadRequest());

        List<DeviceGroup> deviceGroups = deviceGroupRepository.findAll();
        assertThat(deviceGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceGroupRepository.findAll().size();
        // set the field null
        deviceGroup.setGroupName(null);

        // Create the DeviceGroup, which fails.

        restDeviceGroupMockMvc.perform(post("/api/device-groups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deviceGroup)))
                .andExpect(status().isBadRequest());

        List<DeviceGroup> deviceGroups = deviceGroupRepository.findAll();
        assertThat(deviceGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceGroups() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        // Get all the deviceGroups
        restDeviceGroupMockMvc.perform(get("/api/device-groups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(deviceGroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
                .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME.toString())))
                .andExpect(jsonPath("$.[*].groupDescription").value(hasItem(DEFAULT_GROUP_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDeviceGroup() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);

        // Get the deviceGroup
        restDeviceGroupMockMvc.perform(get("/api/device-groups/{id}", deviceGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deviceGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME.toString()))
            .andExpect(jsonPath("$.groupDescription").value(DEFAULT_GROUP_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceGroup() throws Exception {
        // Get the deviceGroup
        restDeviceGroupMockMvc.perform(get("/api/device-groups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceGroup() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);
        int databaseSizeBeforeUpdate = deviceGroupRepository.findAll().size();

        // Update the deviceGroup
        DeviceGroup updatedDeviceGroup = new DeviceGroup();
        updatedDeviceGroup.setId(deviceGroup.getId());
        updatedDeviceGroup.setGroupId(UPDATED_GROUP_ID);
        updatedDeviceGroup.setGroupName(UPDATED_GROUP_NAME);
        updatedDeviceGroup.setGroupDescription(UPDATED_GROUP_DESCRIPTION);

        restDeviceGroupMockMvc.perform(put("/api/device-groups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDeviceGroup)))
                .andExpect(status().isOk());

        // Validate the DeviceGroup in the database
        List<DeviceGroup> deviceGroups = deviceGroupRepository.findAll();
        assertThat(deviceGroups).hasSize(databaseSizeBeforeUpdate);
        DeviceGroup testDeviceGroup = deviceGroups.get(deviceGroups.size() - 1);
        assertThat(testDeviceGroup.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testDeviceGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testDeviceGroup.getGroupDescription()).isEqualTo(UPDATED_GROUP_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteDeviceGroup() throws Exception {
        // Initialize the database
        deviceGroupRepository.saveAndFlush(deviceGroup);
        int databaseSizeBeforeDelete = deviceGroupRepository.findAll().size();

        // Get the deviceGroup
        restDeviceGroupMockMvc.perform(delete("/api/device-groups/{id}", deviceGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeviceGroup> deviceGroups = deviceGroupRepository.findAll();
        assertThat(deviceGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
