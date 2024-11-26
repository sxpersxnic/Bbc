package ch.bbcag.ticketshop.ticket;

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
@RequestMapping(path = TicketController.PATH)
public class TicketController {
    public static final String PATH = "/tickets";
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // GET - find all
    // DOCUMENTATION
    @GetMapping
    @Operation(summary = "Get all tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All tickets found!",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class)))
    })
    // METHOD
    public ResponseEntity<?> findAll() {
        List<Ticket> tickets = ticketService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tickets.stream().map(TicketMapper::toDTO).toList());
    }

    // GET - find by id
    // DOCUMENTATION
    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket found!",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found!",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> findById(@Parameter(description = "Id of ticket to find") @PathVariable Integer id) {
        try {
            Ticket ticket = ticketService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(TicketMapper.toDTO(ticket));
        } catch (EntityNotFoundException e ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found!");
        }
    }

    // GET - findAllUnsold
    // DOCUMENTATION
    @GetMapping("/unsold")
    @Operation(summary = "Get all unsold tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All unsold tickets found!",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "404", description = "Unsold tickets not found!",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> findAllUnsold() {
        try {
            List<Ticket> tickets = ticketService.findAllUnsold();
            return ResponseEntity.status(HttpStatus.OK).body(tickets.stream().map(TicketMapper::toDTO).toList());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unsold tickets not found!");
        }
    }

    // POST - create
    // DOCUMENTATION
    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    @Operation(summary = "Create a new ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully!",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "409", description = "A conflict occurred during creation of new ticket",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> create(
            @Parameter(description = "Ticket to create")
            @Valid @RequestBody TicketDTO newTicketDTO) {
        try {
            Ticket newTicket = TicketMapper.fromDTO(newTicketDTO);
            Ticket savedTicket = ticketService.create(newTicket);
            return ResponseEntity.status(HttpStatus.CREATED).body(TicketMapper.toDTO(savedTicket));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A conflict occurred while creating the ticket");
        }
    }

    // PATCH
    // DOCUMENTATION
    @PatchMapping("/buy")
    @Operation(summary = "Buy tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets purchased successfully!",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tickets could not be found!",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "A conflict occurred while purchasing the tickets",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> buy(@Parameter(description = "Tickets to buy") @RequestBody List<TicketDTO> buyTicketsDTO) {
        try {
            List<Ticket> buyTickets = buyTicketsDTO.stream().map(TicketMapper::fromDTO).toList();
            List<Ticket> savedTickets = ticketService.buy(buyTickets);
            return ResponseEntity.status(HttpStatus.OK).body(savedTickets.stream().map(TicketMapper::toDTO).toList());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets could not be found!");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A conflict occurred while purchasing the ticket");
        }
    }

    // PATCH
    // DOCUMENTATION
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    @Operation(summary = "Update a ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated successfully!",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ticket could not be found!",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "A conflict occurred while updating the ticket",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> update(
            @Parameter(description = "Ticket to update")
            @RequestBody TicketDTO updateTicketDTO,

            @Parameter(description = "Id of ticket to update")
            @PathVariable Integer id
    ) {
        try {
            Ticket updateTicket = TicketMapper.fromDTO(updateTicketDTO);
            Ticket savedTicket = ticketService.update(updateTicket, id);
            return ResponseEntity.status(HttpStatus.OK).body(TicketMapper.toDTO(savedTicket));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A conflict occurred while updating the ticket");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket could not be found!");
        }
    }

    // DELETE
    // DOCUMENTATION
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    @Operation(summary = "Delete an ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket was deleted successfully!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Ticket to delete was not found!",
                    content = @Content)
    })
    // METHOD
    public ResponseEntity<?> delete(@Parameter(description = "Id of ticket to delete") @PathVariable Integer id) {
        try {
            ticketService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket to be deleted was not found!");
        }
    }
}
