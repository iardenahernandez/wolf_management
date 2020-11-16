package com.wolf_pack.wolf_management.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pack", schema = "public")
public class Pack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull(message = "Please, insert a pack name.")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wolf_pack",
            joinColumns = @JoinColumn(name = "pack_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "wolf_id", referencedColumnName = "id"))
    private Set<Wolf> wolves;

    public Set<Wolf> getWolves() {
        return wolves;
    }

    public void setWolves(Set<Wolf> wolves) {
        this.wolves = wolves;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Pack))
            return false;
        Pack pack = (Pack) o;
        return Objects.equals(this.id, pack.id) && Objects.equals(this.name, pack.name)
                && Objects.equals(this.name, pack.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Pack{" + "id=" + this.id + ", name='" + this.name + '}';
    }
}
