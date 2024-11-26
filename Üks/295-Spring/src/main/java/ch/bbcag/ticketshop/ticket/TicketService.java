package ch.bbcag.ticketshop.ticket;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public List<Ticket> findAllUnsold() {
        return ticketRepository.findAllUnsold();
    }

    public Ticket findById(Integer id) {
        return ticketRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket update(Ticket ticket, Integer id) {
        Ticket existing = ticketRepository.findByIdForUpdate(id).orElseThrow(EntityNotFoundException::new);
        mergeTickets(existing, ticket);
        return ticketRepository.save(existing);
    }

    public void deleteById(Integer id) {
        ticketRepository.deleteById(id);
    }

    // TODO
    @Transactional
    public List<Ticket> buy(List<Ticket> tickets) {
        // Erstelle eine Liste mit den IDs zu den Tickets.
        List<Integer> ticketIds = tickets.stream().map(Ticket::getId).collect(Collectors.toList());
        // Lies alle Tickets mithilfe der ticketIds aus der Datenbank.
        List<Ticket> existingTickets = ticketRepository.findAllByIdForUpdate(ticketIds);
        // Überprüfe, ob alle zu kaufenden Tickets gefunden wurden. Falls nicht: Wirf eine EntityNotFoundException.
        if (existingTickets.size() != tickets.size()) {
            throw new EntityNotFoundException("One or more tickets were not found.");
        }
        // Iteriere durch alle zu kaufenden Tickets
        for (Ticket changingTicket : tickets) {
            // Finde zum changingTicket das passende existingTicket
            Ticket existingTicket = existingTickets.stream().filter(ticket -> ticket.getId().equals(changingTicket.getId())).findFirst().orElseThrow(() -> new EntityNotFoundException("Ticket not found: " + changingTicket.getId()));            // In einem changingTicket befindet sich zurzeit die Anzahl an Tickets, welche gekauft werden soll.
            // Ziehe die anzahl tickets vom existierendem Ticket ab
            existingTicket.setAmount(existingTicket.getAmount() - changingTicket.getAmount());
            // Wenn die anzahl noch verfügbarer Tickets kleiner ist als 0, soll eine DataIntegrityViolationException geworfen werden
            if (existingTicket.getAmount() < 0) {
                throw new DataIntegrityViolationException("Ticket sold out: " + existingTicket.getId());
            }
        }
        // Speichere die Tickets mithilfe des Repositories und returne diese.
        return ticketRepository.saveAll(existingTickets);
    }


    private void mergeTickets(Ticket existing, Ticket changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getAmount() != null) {
            if (changing.getAmount() >= 0) {
                existing.setAmount(changing.getAmount());
            } else {
                errors.put("amount", List.of("Amount must be greater than 0"));
            }
        }

        if (changing.getDescription() != null) {
            existing.setDescription(changing.getDescription());
        }

        if (changing.getName() != null) {
            if (StringUtils.isNotBlank(changing.getName())) {
                existing.setName(changing.getName());
            } else {
                errors.put("name", List.of("Name must not be empty."));
            }
        }

        if (changing.getEvent() != null) {
            existing.setEvent(changing.getEvent());
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }

    }

}
