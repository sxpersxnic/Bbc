package ch.bbcag.ticketshop.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public class RoleDTO {

    @NotNull(message = "Id can not be null!")
    private Integer id;

    @NotBlank(message = "Name can not be empty!")
    private String name;
    private List<Integer> assignedPersonIds;

    // Getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getAssignedPersonIds() {
        return assignedPersonIds;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignedPersonIds(List<Integer> assignedPersonIds) {
        this.assignedPersonIds = assignedPersonIds;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(id, roleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
