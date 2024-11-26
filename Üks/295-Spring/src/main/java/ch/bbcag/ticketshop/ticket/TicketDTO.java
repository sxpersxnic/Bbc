package ch.bbcag.ticketshop.ticket;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class TicketDTO {

    @NotNull(message = "Id can not be null!")
    private Integer id;

    @Min(0)
    private Integer amount;

    @NotNull(message = "EventId can not be null!")
    private Integer eventId;
    private String description;

    @NotBlank(message = "Name can not be empty!")
    private String name;

    // No args constructor
    public TicketDTO() {
    }

    // Getter
    public Integer getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getEventId() {
        return eventId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return Objects.equals(id, ticketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
