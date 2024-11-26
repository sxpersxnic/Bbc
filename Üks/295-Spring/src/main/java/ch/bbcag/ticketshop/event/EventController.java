package ch.bbcag.ticketshop.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = EventController.PATH)
public class EventController {
    public static final String PATH = "/events";
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // GET
    // DOCUMENTATION
    @GetMapping("/{id}")
    @Operation(summary = "Get an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event was not found",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> findById(@Parameter(description = "Id of event to get") @PathVariable("id") Integer id) {
        try {
            Event event = eventService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(EventMapper.toDTO(event));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event was not found!");
        }
    }

    // GET
    // DOCUMENTATION
    @GetMapping
    @Operation(summary = "Get all events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events were found",
                content = @Content(schema = @Schema(implementation = EventDTO.class)))
    })
    // METHOD
    public ResponseEntity<?> findAll() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(events.stream().map(EventMapper::toDTO).toList());
    }
    // POST
    // DOCUMENTATION
    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    @Operation(summary = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully!",
                content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "400", description = "Conflict occurred during creation of new event",
                content = @Content)
    })
    // METHOD
    public ResponseEntity<?> create(
            @Parameter(description = "Event to create")
            @Valid @RequestBody EventDTO newEventDTO) {
        try {
            Event newEvent = EventMapper.fromDTO(newEventDTO);
            Event savedEvent = eventService.create(newEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(EventMapper.toDTO(savedEvent));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There occurred a conflict while creating the event");
        }
    }
    // PATCH
    // DOCUMENTATION
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    @Operation(summary = "Update an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully!",
                content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event could not be found!",
                content = @Content),
            @ApiResponse(responseCode = "400", description = "There occurred a conflict while updating the event",
                content = @Content)
    })
    // METHOD
    public ResponseEntity<?> update(
            @Parameter(description = "Event to update")
            @RequestBody EventDTO updateEventDTO,

            @Parameter(description = "Id of event to update")
            @PathVariable Integer id
    ) {
        try {
            Event updateEvent = EventMapper.fromDTO(updateEventDTO);
            Event savedEvent = eventService.update(updateEvent, id);
            return ResponseEntity.status(HttpStatus.OK).body(EventMapper.toDTO(savedEvent));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There occurred a conflict while updating the event");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event could not be found!");
        }
    }
    // DELETE
    // DOCUMENTATION
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    @Operation(summary = "Delete an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event was deleted successfully!",
            content = @Content),
            @ApiResponse(responseCode = "404", description = "Event to delete was not found!",
            content = @Content)
    })
    // METHOD
    public ResponseEntity<?> delete(@Parameter(description = "Id of event to delete") @PathVariable Integer id) {
        try {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event to be deleted was not found!");
        }
    }

}
