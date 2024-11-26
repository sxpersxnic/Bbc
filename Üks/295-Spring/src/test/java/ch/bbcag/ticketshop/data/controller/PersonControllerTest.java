package ch.bbcag.ticketshop.data.controller;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import ch.bbcag.ticketshop.lib.exceptions.GlobalControllerExceptionHandler;
import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.person.PersonController;
import ch.bbcag.ticketshop.person.PersonService;
import ch.bbcag.ticketshop.person.dto.PersonRequestDTO;
import ch.bbcag.ticketshop.person.dto.PersonResponseDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@AutoConfigureMockMvc(addFilters = false)
//@WithMockUser(authorities = "SCOPE_ROLENAME")

@WithMockUser(authorities = {"SCOPE_MANAGER", "SCOPE_ADMIN"})
@ContextConfiguration(classes = {SecurityConfiguration.class})
@Import({PersonController.class, GlobalControllerExceptionHandler.class})
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGet_whenNoParam_thenAllPersonsAreReturned() throws Exception {
        List<Person> persons = DataUtil.getTestPersons();
        List<PersonResponseDTO> personDTOs = DataDTOUtil.getTestPersonResponseDTOs();
        String expectedResponseBody = objectMapper.writeValueAsString(personDTOs);

        when(personService.findAll()).thenReturn(persons);

        mockMvc.perform(get("/persons")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkGetById_whenValidId_thenPersonIsReturned() throws Exception {
        Person person = DataUtil.getTestPerson();
        PersonResponseDTO responseDTO = DataDTOUtil.getTestPersonResponseDTO();
        String expectedResponseBody = objectMapper.writeValueAsString(responseDTO);
        when(personService.findById(eq(1))).thenReturn(person);

        mockMvc.perform(get("/persons/" + 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        when(personService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/persons/0")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPatch_whenValidPerson_thenIsOk() throws Exception {
        Person person = DataUtil.getTestPerson();
        person.setEmail("NewPerson@bbcag.ch");
        PersonResponseDTO personResponseDTO = DataDTOUtil.getTestPersonResponseDTO();
        PersonResponseDTO personRequestDTO = DataDTOUtil.getTestPersonRequestDTO();
        personResponseDTO.setEmail("NewPerson@bbcag.ch");
        String expectedResponseBody = objectMapper.writeValueAsString(personResponseDTO);
        String requestBody = objectMapper.writeValueAsString(personRequestDTO);

        when(personService.update(any(Person.class), eq(1))).thenReturn(person);

        mockMvc.perform(patch("/persons/update/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody));
    }

    @Test
    public void checkPatch_whenInvalidPerson_thenIsBadRequest() throws Exception {
        PersonRequestDTO personRequestDTO = DataDTOUtil.getTestPersonRequestDTO();
        personRequestDTO.setEmail("");
        String requestBody = objectMapper.writeValueAsString(personRequestDTO);

        when(personService.update(any(Person.class), eq(1))).thenThrow(new FailedValidationException(Map.of()));

        mockMvc.perform(patch("/persons/update/1").
                        contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPatch_whenEventDoesntExist_thenIsNotFound() throws Exception {
        PersonRequestDTO personRequestDTO = DataDTOUtil.getTestPersonRequestDTO();
        String requestBody = objectMapper.writeValueAsString(personRequestDTO);

        when(personService.update(any(Person.class), eq(1))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(patch("/persons/update/1").
                        contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }


    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/persons/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(personService).deleteById(0);

        mockMvc.perform(delete("/persons/delete/0"))
                .andExpect(status().isNotFound());
    }
}
