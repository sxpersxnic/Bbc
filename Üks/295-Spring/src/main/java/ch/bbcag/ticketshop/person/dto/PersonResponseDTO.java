package ch.bbcag.ticketshop.person.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public class PersonResponseDTO {

    @NotNull(message = "Id can not be empty!")
    private Integer id;

    @Email(message = "Invalid email!", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotBlank(message = "Email can not be empty!")
    private String email;
    private List<Integer> eventIds;
    private List<Integer> roleIds;

    // Getter
    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public List<Integer> getEventIds() {
        return eventIds;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setEventIds(List<Integer> eventIds) {
        this.eventIds = eventIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonResponseDTO that = (PersonResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
