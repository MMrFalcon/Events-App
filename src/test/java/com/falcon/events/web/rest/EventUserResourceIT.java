package com.falcon.events.web.rest;

import com.falcon.events.EventsApp;
import com.falcon.events.domain.EventUser;
import com.falcon.events.repository.EventUserRepository;
import com.falcon.events.service.EventUserService;
import com.falcon.events.service.dto.EventUserDTO;
import com.falcon.events.service.mapper.EventUserMapper;
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
 * Integration tests for the {@Link EventUserResource} REST controller.
 */
@SpringBootTest(classes = EventsApp.class)
public class EventUserResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    @Autowired
    private EventUserRepository eventUserRepository;

    @Autowired
    private EventUserMapper eventUserMapper;

    @Autowired
    private EventUserService eventUserService;

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

    private MockMvc restEventUserMockMvc;

    private EventUser eventUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventUserResource eventUserResource = new EventUserResource(eventUserService);
        this.restEventUserMockMvc = MockMvcBuilders.standaloneSetup(eventUserResource)
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
    public static EventUser createEntity(EntityManager em) {
        EventUser eventUser = new EventUser()
            .username(DEFAULT_USERNAME);
        return eventUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventUser createUpdatedEntity(EntityManager em) {
        EventUser eventUser = new EventUser()
            .username(UPDATED_USERNAME);
        return eventUser;
    }

    @BeforeEach
    public void initTest() {
        eventUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventUser() throws Exception {
        int databaseSizeBeforeCreate = eventUserRepository.findAll().size();

        // Create the EventUser
        EventUserDTO eventUserDTO = eventUserMapper.toDto(eventUser);
        restEventUserMockMvc.perform(post("/api/event-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventUserDTO)))
            .andExpect(status().isCreated());

        // Validate the EventUser in the database
        List<EventUser> eventUserList = eventUserRepository.findAll();
        assertThat(eventUserList).hasSize(databaseSizeBeforeCreate + 1);
        EventUser testEventUser = eventUserList.get(eventUserList.size() - 1);
        assertThat(testEventUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    @Transactional
    public void createEventUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventUserRepository.findAll().size();

        // Create the EventUser with an existing ID
        eventUser.setId(1L);
        EventUserDTO eventUserDTO = eventUserMapper.toDto(eventUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventUserMockMvc.perform(post("/api/event-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventUser in the database
        List<EventUser> eventUserList = eventUserRepository.findAll();
        assertThat(eventUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEventUsers() throws Exception {
        // Initialize the database
        eventUserRepository.saveAndFlush(eventUser);

        // Get all the eventUserList
        restEventUserMockMvc.perform(get("/api/event-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEventUser() throws Exception {
        // Initialize the database
        eventUserRepository.saveAndFlush(eventUser);

        // Get the eventUser
        restEventUserMockMvc.perform(get("/api/event-users/{id}", eventUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventUser() throws Exception {
        // Get the eventUser
        restEventUserMockMvc.perform(get("/api/event-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventUser() throws Exception {
        // Initialize the database
        eventUserRepository.saveAndFlush(eventUser);

        int databaseSizeBeforeUpdate = eventUserRepository.findAll().size();

        // Update the eventUser
        EventUser updatedEventUser = eventUserRepository.findById(eventUser.getId()).get();
        // Disconnect from session so that the updates on updatedEventUser are not directly saved in db
        em.detach(updatedEventUser);
        updatedEventUser
            .username(UPDATED_USERNAME);
        EventUserDTO eventUserDTO = eventUserMapper.toDto(updatedEventUser);

        restEventUserMockMvc.perform(put("/api/event-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventUserDTO)))
            .andExpect(status().isOk());

        // Validate the EventUser in the database
        List<EventUser> eventUserList = eventUserRepository.findAll();
        assertThat(eventUserList).hasSize(databaseSizeBeforeUpdate);
        EventUser testEventUser = eventUserList.get(eventUserList.size() - 1);
        assertThat(testEventUser.getUsername()).isEqualTo(UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEventUser() throws Exception {
        int databaseSizeBeforeUpdate = eventUserRepository.findAll().size();

        // Create the EventUser
        EventUserDTO eventUserDTO = eventUserMapper.toDto(eventUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventUserMockMvc.perform(put("/api/event-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventUser in the database
        List<EventUser> eventUserList = eventUserRepository.findAll();
        assertThat(eventUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventUser() throws Exception {
        // Initialize the database
        eventUserRepository.saveAndFlush(eventUser);

        int databaseSizeBeforeDelete = eventUserRepository.findAll().size();

        // Delete the eventUser
        restEventUserMockMvc.perform(delete("/api/event-users/{id}", eventUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventUser> eventUserList = eventUserRepository.findAll();
        assertThat(eventUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventUser.class);
        EventUser eventUser1 = new EventUser();
        eventUser1.setId(1L);
        EventUser eventUser2 = new EventUser();
        eventUser2.setId(eventUser1.getId());
        assertThat(eventUser1).isEqualTo(eventUser2);
        eventUser2.setId(2L);
        assertThat(eventUser1).isNotEqualTo(eventUser2);
        eventUser1.setId(null);
        assertThat(eventUser1).isNotEqualTo(eventUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventUserDTO.class);
        EventUserDTO eventUserDTO1 = new EventUserDTO();
        eventUserDTO1.setId(1L);
        EventUserDTO eventUserDTO2 = new EventUserDTO();
        assertThat(eventUserDTO1).isNotEqualTo(eventUserDTO2);
        eventUserDTO2.setId(eventUserDTO1.getId());
        assertThat(eventUserDTO1).isEqualTo(eventUserDTO2);
        eventUserDTO2.setId(2L);
        assertThat(eventUserDTO1).isNotEqualTo(eventUserDTO2);
        eventUserDTO1.setId(null);
        assertThat(eventUserDTO1).isNotEqualTo(eventUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventUserMapper.fromId(null)).isNull();
    }
}
