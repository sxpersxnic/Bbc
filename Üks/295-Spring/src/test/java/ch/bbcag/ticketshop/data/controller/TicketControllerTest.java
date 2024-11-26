package ch.bbcag.ticketshop.data.controller;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;

import ch.bbcag.ticketshop.lib.exceptions.GlobalControllerExceptionHandler;
import ch.bbcag.ticketshop.security.SecurityConfiguration;
import ch.bbcag.ticketshop.ticket.Ticket;
import ch.bbcag.ticketshop.ticket.TicketController;
import ch.bbcag.ticketshop.ticket.TicketDTO;
import ch.bbcag.ticketshop.ticket.TicketService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@AutoConfigureMockMvc(addFilters = false)
//@WithMockUser(authorities = "SCOPE_ROLENAME")
@WithMockUser(authorities = {"SCOPE_MANAGER", "SCOPE_ADMIN"})
@ContextConfiguration(classes = {SecurityConfiguration.class})
@Import({TicketController.class, GlobalControllerExceptionHandler.class})
@WebMvcTest(controllers = TicketController.class)
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGet_whenNoParam_thenAllTicketsAreReturned() throws Exception {
        List<Ticket> tickets = DataUtil.getTestTickets();
        List<TicketDTO> ticketDTOs = DataDTOUtil.getTestTicketDTOs();
        String expectedResponseBody = objectMapper.writeValueAsString(ticketDTOs);

        when(ticketService.findAll()).thenReturn(tickets);

        mockMvc.perform(get("/tickets")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkGetById_whenValidId_thenTicketIsReturned() throws Exception {
        Ticket ticket = DataUtil.getTestTicket();
        TicketDTO ticketDTO = DataDTOUtil.getTestTicketDTO();
        String expectedResponseBody = objectMapper.writeValueAsString(ticketDTO);
        when(ticketService.findById(eq(1))).thenReturn(ticket);

        mockMvc.perform(get("/tickets/" + 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        when(ticketService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/tickets/0")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkGet_whenLookingForUnsold_thenAllUnsoldTicketsAreReturned() throws Exception {
        List<Ticket> tickets = DataUtil.getTestTickets();
        List<TicketDTO> ticketDTOs = DataDTOUtil.getTestTicketDTOs();
        String expectedResponse = objectMapper.writeValueAsString(ticketDTOs);

        when (ticketService.findAllUnsold()).thenReturn(tickets);

        mockMvc.perform(get("/tickets/unsold")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void checkPost_whenValidTicket_thenIsCreated() throws Exception {
        Ticket ticket = DataUtil.getTestTicket();
        TicketDTO ticketDTO = DataDTOUtil.getTestTicketDTO();
        String expectedResponseBody = objectMapper.writeValueAsString(ticketDTO);
        String requestBody = objectMapper.writeValueAsString(ticketDTO);

        when(ticketService.create(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/tickets/new")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkPost_whenInvalidTicket_thenIsBadRequest() throws Exception {
        TicketDTO ticketDTO = DataDTOUtil.getTestTicketDTO();
        ticketDTO.setName("");
        String requestBody = objectMapper.writeValueAsString(ticketDTO);

        when(ticketService.create(any(Ticket.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/tickets/new")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPost_whenInvalidTicketId_thenIsConflict() throws Exception {
        TicketDTO ticketDTO = DataDTOUtil.getTestTicketDTO();
        ticketDTO.setEventId(0);
        String requestBody = objectMapper.writeValueAsString(ticketDTO);

        when(ticketService.create(any(Ticket.class))).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/tickets/new")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isConflict());
    }

    @Test
    public void checkPatch_whenValidTicketPurchase_thenIsOk() throws Exception {
        List<Ticket> tickets = DataUtil.getTestTickets();
        List<TicketDTO> ticketDTOs = DataDTOUtil.getTestTicketDTOs();
        String expectedResponse = objectMapper.writeValueAsString(ticketDTOs);
        String requestBody = objectMapper.writeValueAsString(ticketDTOs);

        when(ticketService.buy(any(List.class))).thenReturn(tickets);

        mockMvc.perform(patch("/tickets/buy")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void checkPatch_whenValidTicket_thenIsOk() throws Exception {
        Ticket ticket = DataUtil.getTestTicket();
        ticket.setName("NewTicketName");
        TicketDTO ticketDTO = DataDTOUtil.getTestTicketDTO();
        TicketDTO ticketRequestDTO = DataDTOUtil.getTestTicketDTO();
        ticketDTO.setName("NewTicketName");
        String expectedResponseBody = objectMapper.writeValueAsString(ticketDTO);
        String requestBody = objectMapper.writeValueAsString(ticketRequestDTO);

        when(ticketService.update(any(Ticket.class), eq(1))).thenReturn(ticket);

        mockMvc.perform(patch("/tickets/update/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkPatch_whenInvalidTicket_thenIsBadRequest() throws Exception {
        TicketDTO ticketDTO = DataDTOUtil.getTestTicketDTO();
        ticketDTO.setName("");
        String requestBody = objectMapper.writeValueAsString(ticketDTO);

        when(ticketService.update(any(Ticket.class), eq(1))).thenThrow(new FailedValidationException(Map.of()));

        mockMvc.perform(patch("/tickets/update/1").
                        contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPatch_whenTicketDoesntExist_thenIsNotFound() throws Exception {
        TicketDTO ticketDTO = DataDTOUtil.getTestTicketDTO();
        String requestBody = objectMapper.writeValueAsString(ticketDTO);

        when(ticketService.update(any(Ticket.class), eq(1))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(patch("/tickets/update/1").
                        contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }


    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/tickets/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(ticketService).deleteById(0);

        mockMvc.perform(delete("/tickets/delete/0"))
                .andExpect(status().isNotFound());
    }
}
