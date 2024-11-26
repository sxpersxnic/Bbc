package ch.bbcag.ticketshop.data.controller;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import ch.bbcag.ticketshop.event.Event;
import ch.bbcag.ticketshop.event.EventController;
import ch.bbcag.ticketshop.event.EventDTO;
import ch.bbcag.ticketshop.event.EventService;
import ch.bbcag.ticketshop.lib.exceptions.GlobalControllerExceptionHandler;
import ch.bbcag.ticketshop.security.SecurityConfiguration;
import ch.bbcag.ticketshop.util.DataDTOUtil;
import ch.bbcag.ticketshop.util.DataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@AutoConfigureMockMvc(addFilters = false)
//@WithMockUser(authorities = "SCOPE_ROLENAME")
@WithMockUser(authorities = {"SCOPE_MANAGER", "SCOPE_ADMIN"})
@ContextConfiguration(classes = {SecurityConfiguration.class})
@Import({EventController.class, GlobalControllerExceptionHandler.class})
@WebMvcTest(controllers = EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGet_whenNoParam_thenAllEventsAreReturned() throws Exception {
        List<Event> events = DataUtil.getTestEvents();
        List<EventDTO> eventDTOs = DataDTOUtil.getTestEventDTOs();
        String expectedResponseBody = objectMapper.writeValueAsString(eventDTOs);

        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(get("/events")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkGetById_whenValidId_thenEventIsReturned() throws Exception {
        Event event = DataUtil.getTestEvent();
        EventDTO eventDTO = DataDTOUtil.getTestEventDTO();
        String expectedResponseBody = objectMapper.writeValueAsString(eventDTO);
        when(eventService.findById(eq(1))).thenReturn(event);

        mockMvc.perform(get("/events/" + 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        when(eventService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/events/0")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPost_whenValidEvent_thenIsCreated() throws Exception {
        Event event = DataUtil.getTestEvent();
        EventDTO eventDTO = DataDTOUtil.getTestEventDTO();
        String expectedResponseBody = objectMapper.writeValueAsString(eventDTO);
        String requestBody = objectMapper.writeValueAsString(eventDTO);

        when(eventService.create(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/events/new")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkPost_whenInvalidEvent_thenIsBadRequest() throws Exception {
        EventDTO eventDTO = DataDTOUtil.getTestEventDTO();
        eventDTO.setName("");
        String requestBody = objectMapper.writeValueAsString(eventDTO);

        when(eventService.create(any(Event.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/events/new")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPost_whenInvalidOwnerId_thenIsConflict() throws Exception {
        EventDTO eventDTO = DataDTOUtil.getTestEventDTO();
        eventDTO.setOwnerId(0);
        String requestBody = objectMapper.writeValueAsString(eventDTO);

        when(eventService.create(any(Event.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/events/new")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPatch_whenValidEvent_thenIsOk() throws Exception {
        Event event = DataUtil.getTestEvent();
        event.setName("NewEventName");
        EventDTO eventDTO = DataDTOUtil.getTestEventDTO();
        eventDTO.setName("NewEventName");
        String expectedResponseBody = objectMapper.writeValueAsString(eventDTO);
        String requestBody = objectMapper.writeValueAsString(eventDTO);

        when(eventService.update(any(Event.class), eq(1))).thenReturn(event);

        mockMvc.perform(patch("/events/update/1").
                        contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkPatch_whenInvalidEvent_thenIsBadRequest() throws Exception {
        EventDTO eventDTO = DataDTOUtil.getTestEventDTO();
        eventDTO.setName("");
        String requestBody = objectMapper.writeValueAsString(eventDTO);

        when(eventService.update(any(Event.class), eq(1))).thenThrow(new FailedValidationException(Map.of()));

        mockMvc.perform(patch("/events/update/1").
                        contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPatch_whenEventDoesntExist_thenIsNotFound() throws Exception {
        EventDTO eventDTO = DataDTOUtil.getTestEventDTO();
        String requestBody = objectMapper.writeValueAsString(eventDTO);

        when(eventService.update(any(Event.class), eq(1))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(patch("/events/update/1").
                        contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }


    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/events/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(eventService).deleteById(0);

        mockMvc.perform(delete("/events/delete/0"))
                .andExpect(status().isNotFound());
    }

}
