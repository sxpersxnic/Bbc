package ch.bbcag.ticketshop.person;

import ch.bbcag.ticketshop.person.dto.PersonRequestDTO;
import ch.bbcag.ticketshop.person.dto.PersonResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping(path = PersonController.PATH)
public class PersonController {
    public static final String PATH = "/persons";
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // GET
    // DOCUMENTATION
    @GetMapping
    @Operation(summary = "Get all persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All persons found!",
                content = @Content(schema = @Schema(implementation = PersonResponseDTO.class)))
    })
    // METHOD
    public ResponseEntity<?> findAll() {
        List<Person> persons = personService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(persons.stream().map(PersonMapper::toDTO).toList());
    }
    // GET
    // DOCUMENTATION
    @GetMapping("/{id}")
    @Operation(summary = "Get person by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found!",
                    content = @Content(schema = @Schema(implementation = PersonResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Person not found!",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> findById(@Parameter(description = "Id of person to find") @PathVariable Integer id) {
        try {
            Person person = personService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(PersonMapper.toDTO(person));
        } catch (EntityNotFoundException e ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found!");
        }
    }
    // PATCH
    // DOCUMENTATION
    @PatchMapping("/update/{id}")
    @Operation(summary = "Update a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated successfully!",
                    content = @Content(schema = @Schema(implementation = PersonResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Person could not be found!",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "There occurred a conflict while updating the person",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> update(
            @Parameter(description = "Person to update")
            @RequestBody PersonRequestDTO updatePersonDTO,

            @Parameter(description = "Id of person to update")
            @PathVariable Integer id
    ) {
        try {
            Person updatePerson = PersonMapper.fromDTO(updatePersonDTO);
            Person savedPerson = personService.update(updatePerson, id);
            return ResponseEntity.status(HttpStatus.OK).body(PersonMapper.toDTO(savedPerson));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found!");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There occurred a conflict while updating the person");
        }
    }

    // DELETE
    // DOCUMENTATION
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Person could not be deleted",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> delete(@Parameter(description = "Id of person to delete") @PathVariable Integer id) {
        try {
            personService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person to be deleted was not found!");
        }
    }
}
