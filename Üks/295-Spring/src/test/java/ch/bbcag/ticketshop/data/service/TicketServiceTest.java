package ch.bbcag.ticketshop.data.service;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import ch.bbcag.ticketshop.ticket.Ticket;
import ch.bbcag.ticketshop.ticket.TicketRepository;
import ch.bbcag.ticketshop.ticket.TicketService;
import ch.bbcag.ticketshop.util.DataUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void checkFindAll_whenTicketsExist_thenTicketsAreReturned() {
        List<Ticket> expectedTickets = DataUtil.getTestTickets();
        when(ticketRepository.findAll()).thenReturn(expectedTickets);

        List<Ticket> actualTickets = ticketService.findAll();

        assertEquals(expectedTickets.size(), actualTickets.size());
        for (int i = 0; i < expectedTickets.size(); i++) {
            Ticket expectedTicket = expectedTickets.get(i);
            Ticket actualTicket = actualTickets.get(i);

            assertEquals(expectedTicket.getId(), actualTicket.getId());
            assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
            assertEquals(expectedTicket.getDescription(), actualTicket.getDescription());
            assertEquals(expectedTicket.getName(), actualTicket.getName());
            assertEquals(expectedTicket.getEvent(), actualTicket.getEvent());
        }
    }

    @Test
    public void checkFindAllUnsold_whenTicketsExist_thenTicketsAreReturned() {
        List<Ticket> expectedTickets = DataUtil.getTestTickets();
        when(ticketRepository.findAllUnsold()).thenReturn(expectedTickets);

        List<Ticket> actualTickets = ticketService.findAllUnsold();

        assertEquals(expectedTickets.size(), actualTickets.size());
        for (int i = 0; i < expectedTickets.size(); i++) {
            Ticket expectedTicket = expectedTickets.get(i);
            Ticket actualTicket = actualTickets.get(i);

            assertEquals(expectedTicket.getId(), actualTicket.getId());
            assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
            assertEquals(expectedTicket.getDescription(), actualTicket.getDescription());
            assertEquals(expectedTicket.getName(), actualTicket.getName());
            assertEquals(expectedTicket.getEvent(), actualTicket.getEvent());
        }
    }

    @Test
    public void checkFindById_whenExistingId_thenTicketIsReturned() {
        Ticket expectedTicket = DataUtil.getTestTicket();
        when(ticketRepository.findById(eq(1))).thenReturn(Optional.of(expectedTicket));

        Ticket actualTicket = ticketService.findById(1);

        assertNotNull(actualTicket);
        assertEquals(expectedTicket.getId(), actualTicket.getId());
        assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
        assertEquals(expectedTicket.getDescription(), actualTicket.getDescription());
        assertEquals(expectedTicket.getName(), actualTicket.getName());
        assertEquals(expectedTicket.getEvent(), actualTicket.getEvent());
    }

    @Test
    public void checkFindById_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        when(ticketRepository.findById(eq(0))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> ticketService.findById(0));
    }

    @Test
    public void checkCreate_whenValidTicket_thenTicketIsReturned() {
        Ticket expectedTicket = DataUtil.getTestTicket();
        when(ticketRepository.save(expectedTicket)).thenReturn(expectedTicket);

        Ticket actualTicket = ticketService.create(expectedTicket);

        assertNotNull(actualTicket);
        assertEquals(expectedTicket.getId(), actualTicket.getId());
        assertEquals(expectedTicket.getId(), actualTicket.getId());
        assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
        assertEquals(expectedTicket.getDescription(), actualTicket.getDescription());
        assertEquals(expectedTicket.getName(), actualTicket.getName());
        assertEquals(expectedTicket.getEvent(), actualTicket.getEvent());
    }

    @Test
    public void checkCreate_whenEmailAlreadyExisting_thenThrowConstraintViolation() {
        Ticket Ticket = DataUtil.getTestTicket();

        when(ticketRepository.save(eq(Ticket))).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> ticketService.create(Ticket));
    }

    @Test
    public void checkUpdate_whenValidTicket_thenChangedTicketIsReturned() {
        String newDescription = "test";

        Ticket unchangedTicket = DataUtil.getTestTicket();
        when(ticketRepository.findByIdForUpdate(eq(1))).thenReturn(Optional.of(unchangedTicket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        Ticket changedTicket = DataUtil.getTestTicket();
        changedTicket.setDescription(newDescription);
        Ticket updatedTicket = ticketService.update(changedTicket, 1);

        assertEquals(updatedTicket.getDescription(), newDescription);
    }

    @Test
    public void checkUpdate_whenBlankName_thenFailedValidationExceptionIsThrown() {
        Ticket unchangedTicket = DataUtil.getTestTicket();
        when(ticketRepository.findByIdForUpdate(eq(1))).thenReturn(Optional.of(unchangedTicket));

        Ticket changedTicket = DataUtil.getTestTicket();
        changedTicket.setName("");

        assertThrows(FailedValidationException.class, () -> ticketService.update(changedTicket, 1));
    }

    @Test
    public void checkUpdate_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        Ticket Ticket = DataUtil.getTestTicket();
        when(ticketRepository.findByIdForUpdate(eq(0))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> ticketService.update(Ticket, 0));
    }

    @Test
    public void checkDeleteById_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        doThrow(new EntityNotFoundException()).when(ticketRepository).deleteById(0);
        assertThrows(EntityNotFoundException.class, () -> ticketService.deleteById(0));
    }


    @Test
    public void checkBuy_whenValidTickets_thenReturnTickets() {
        List<Ticket> existingTickets = DataUtil.getTestTickets();
        List<Ticket> unchangedTickets = DataUtil.getTestTickets();
        List<Ticket> buyingTickets = DataUtil.getTestTickets();
        buyingTickets.forEach(ticketDTO -> ticketDTO.setAmount(1));

        when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(existingTickets);
        when(ticketRepository.saveAll(any())).then(i -> i.getArgument(0));

        List<Ticket> actualTickets = ticketService.buy(buyingTickets);

        assertEquals(buyingTickets.size(), actualTickets.size());

        for (int i = 0; i < buyingTickets.size(); i++) {
            Ticket boughtTicket = buyingTickets.get(i);
            Ticket existingTicket = existingTickets.get(i);
            Ticket unchangedTicket = unchangedTickets.get(i);
            Ticket actualTicket = actualTickets.get(i);

            assertEquals(boughtTicket.getId(), actualTicket.getId());
            assertEquals(unchangedTicket.getAmount() - boughtTicket.getAmount(), actualTicket.getAmount());
            assertEquals(unchangedTicket.getAmount() - boughtTicket.getAmount(), existingTicket.getAmount());

            assertEquals(boughtTicket.getDescription(), actualTicket.getDescription());
            assertEquals(boughtTicket.getName(), actualTicket.getName());
            assertEquals(boughtTicket.getEvent(), actualTicket.getEvent());
        }
    }


    @Test
    public void checkBuy_whenBuyingTooMuch_thenThrowDataIntegrityViolation() {
        List<Ticket> existingTickets = DataUtil.getTestTickets();
        List<Ticket> buyingTickets = DataUtil.getTestTickets();
        buyingTickets.forEach(ticketDTO -> ticketDTO.setAmount(100));

        when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(existingTickets);
        when(ticketRepository.saveAll(any())).then(i -> i.getArgument(0));

        assertThrows(DataIntegrityViolationException.class, () -> ticketService.buy(buyingTickets));
    }

}
