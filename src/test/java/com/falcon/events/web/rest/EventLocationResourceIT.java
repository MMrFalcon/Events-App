package com.falcon.events.web.rest;

import com.falcon.events.EventsApp;
import com.falcon.events.domain.EventLocation;
import com.falcon.events.repository.EventLocationRepository;
import com.falcon.events.service.EventLocationService;
import com.falcon.events.service.dto.EventLocationDTO;
import com.falcon.events.service.mapper.EventLocationMapper;
import com.falcon.events.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.falcon.events.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EventLocationResource} REST controller.
 */
@SpringBootTest(classes = EventsApp.class)
public class EventLocationResourceIT {

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_EVENT_DAY_OF_WEEK = 1;
    private static final Integer UPDATED_EVENT_DAY_OF_WEEK = 2;

    @Autowired
    private EventLocationRepository eventLocationRepository;

    @Autowired
    private EventLocationMapper eventLocationMapper;

    @Autowired
    private EventLocationService eventLocationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEventLocationMockMvc;

    private EventLocation eventLocation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventLocationResource eventLocationResource = new EventLocationResource(eventLocationService);
        this.restEventLocationMockMvc = MockMvcBuilders.standaloneSetup(eventLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventLocation createEntity(EntityManager em) {
        EventLocation eventLocation = new EventLocation()
            .locationName(DEFAULT_LOCATION_NAME)
            .eventDayOfWeek(DEFAULT_EVENT_DAY_OF_WEEK);
        return eventLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventLocation createUpdatedEntity(EntityManager em) {
        EventLocation eventLocation = new EventLocation()
            .locationName(UPDATED_LOCATION_NAME)
            .eventDayOfWeek(UPDATED_EVENT_DAY_OF_WEEK);
        return eventLocation;
    }

    @BeforeEach
    public void initTest() {
        eventLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventLocation() throws Exception {
        int databaseSizeBeforeCreate = eventLocationRepository.findAll().size();

        // Create the EventLocation
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);
        restEventLocationMockMvc.perform(post("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeCreate + 1);
        EventLocation testEventLocation = eventLocationList.get(eventLocationList.size() - 1);
        assertThat(testEventLocation.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
        assertThat(testEventLocation.getEventDayOfWeek()).isEqualTo(DEFAULT_EVENT_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void createEventLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventLocationRepository.findAll().size();

        // Create the EventLocation with an existing ID
        eventLocation.setId(1L);
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventLocationMockMvc.perform(post("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLocationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLocationRepository.findAll().size();
        // set the field null
        eventLocation.setLocationName(null);

        // Create the EventLocation, which fails.
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);

        restEventLocationMockMvc.perform(post("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isBadRequest());

        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventLocations() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        // Get all the eventLocationList
        restEventLocationMockMvc.perform(get("/api/event-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].eventDayOfWeek").value(hasItem(DEFAULT_EVENT_DAY_OF_WEEK)));
    }
    
    @Test
    @Transactional
    public void getEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        // Get the eventLocation
        restEventLocationMockMvc.perform(get("/api/event-locations/{id}", eventLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventLocation.getId().intValue()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME.toString()))
            .andExpect(jsonPath("$.eventDayOfWeek").value(DEFAULT_EVENT_DAY_OF_WEEK));
    }

    @Test
    @Transactional
    public void getNonExistingEventLocation() throws Exception {
        // Get the eventLocation
        restEventLocationMockMvc.perform(get("/api/event-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        int databaseSizeBeforeUpdate = eventLocationRepository.findAll().size();

        // Update the eventLocation
        EventLocation updatedEventLocation = eventLocationRepository.findById(eventLocation.getId()).get();
        // Disconnect from session so that the updates on updatedEventLocation are not directly saved in db
        em.detach(updatedEventLocation);
        updatedEventLocation
            .locationName(UPDATED_LOCATION_NAME)
            .eventDayOfWeek(UPDATED_EVENT_DAY_OF_WEEK);
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(updatedEventLocation);

        restEventLocationMockMvc.perform(put("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isOk());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeUpdate);
        EventLocation testEventLocation = eventLocationList.get(eventLocationList.size() - 1);
        assertThat(testEventLocation.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
        assertThat(testEventLocation.getEventDayOfWeek()).isEqualTo(UPDATED_EVENT_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void updateNonExistingEventLocation() throws Exception {
        int databaseSizeBeforeUpdate = eventLocationRepository.findAll().size();

        // Create the EventLocation
        EventLocationDTO eventLocationDTO = eventLocationMapper.toDto(eventLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventLocationMockMvc.perform(put("/api/event-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventLocation in the database
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventLocation() throws Exception {
        // Initialize the database
        eventLocationRepository.saveAndFlush(eventLocation);

        int databaseSizeBeforeDelete = eventLocationRepository.findAll().size();

        // Delete the eventLocation
        restEventLocationMockMvc.perform(delete("/api/event-locations/{id}", eventLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventLocation> eventLocationList = eventLocationRepository.findAll();
        assertThat(eventLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventLocation.class);
        EventLocation eventLocation1 = new EventLocation();
        eventLocation1.setId(1L);
        EventLocation eventLocation2 = new EventLocation();
        eventLocation2.setId(eventLocation1.getId());
        assertThat(eventLocation1).isEqualTo(eventLocation2);
        eventLocation2.setId(2L);
        assertThat(eventLocation1).isNotEqualTo(eventLocation2);
        eventLocation1.setId(null);
        assertThat(eventLocation1).isNotEqualTo(eventLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventLocationDTO.class);
        EventLocationDTO eventLocationDTO1 = new EventLocationDTO();
        eventLocationDTO1.setId(1L);
        EventLocationDTO eventLocationDTO2 = new EventLocationDTO();
        assertThat(eventLocationDTO1).isNotEqualTo(eventLocationDTO2);
        eventLocationDTO2.setId(eventLocationDTO1.getId());
        assertThat(eventLocationDTO1).isEqualTo(eventLocationDTO2);
        eventLocationDTO2.setId(2L);
        assertThat(eventLocationDTO1).isNotEqualTo(eventLocationDTO2);
        eventLocationDTO1.setId(null);
        assertThat(eventLocationDTO1).isNotEqualTo(eventLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventLocationMapper.fromId(null)).isNull();
    }
}
