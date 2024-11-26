package ch.bbcag.ticketshop.event;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event findById(Integer id) {
        return eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Event create(Event newEvent) {
        return eventRepository.save(newEvent);
    }

    public Event update(Event event, Integer id) {
        Event existing = this.findById(id);
        mergeEvent(existing, event);
        return eventRepository.save(existing);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    private void mergeEvent(Event existing, Event changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getName() != null) {
            if (StringUtils.isNotBlank(changing.getName())) {
                existing.setName(changing.getName());
            } else {
                errors.put("name", List.of("Name can not be empty!"));
            }
        }
        if (changing.getDescription() != null) {
            existing.setDescription(changing.getDescription());
        }
        if (changing.getDate() != null) {
            if (StringUtils.isNotBlank(changing.getDate().toString())) {
            existing.setDate(changing.getDate());
            } else {
                errors.put("date", List.of("Date can not be empty!"));
            }
        }
        if (changing.getOwner() != null) {
            existing.setOwner(changing.getOwner());
        } else if (changing.getOwner() == null && existing.getOwner() == null) {
            errors.put("owner", List.of("Owner can not be empty!"));
        }
        if (changing.getTickets() != null) {
            existing.setTickets(changing.getTickets());
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }
    }

}
