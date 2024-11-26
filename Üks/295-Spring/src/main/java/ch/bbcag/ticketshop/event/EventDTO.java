package ch.bbcag.ticketshop.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class EventDTO {
    @NotNull(message = "Id can not be empty!")
    private Integer id;

    @NotNull(message = "OwnerId can not be empty!")
    private Integer ownerId;
    private List<Integer> ticketIds;

    @NotBlank(message = "Name can not be empty!")
    private String name;
    private String description;

    @NotNull(message = "Date can not be empty!")
    private LocalDate date;

    // No args constructor
    public EventDTO() {
    }

    // Getter
    public Integer getId() {
        return id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public List<Integer> getTicketIds() {
        return ticketIds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public void setTicketIds(List<Integer> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDTO eventDTO = (EventDTO) o;
        return Objects.equals(id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
