package com.wolf_pack.wolf_management.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wolf_pack.wolf_management.Enum.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "wolf", schema = "public")
public class Wolf implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please, insert a valid name.")
    private String name;

    @Column(columnDefinition = "varchar(32) default 'UNKNOWN'")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please, provide a valid data. Format: yyyy-MM-dd.")
    private Date birthDate;

    @Pattern(regexp = "^([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)$", message = "Enter a correct GSP coordinate, please.")
    @NotEmpty(message = "Enter a correct GSP coordinate, please.")
    @NotNull
    private String location;

    @ManyToMany(mappedBy = "wolves")
    private Set<Pack> packs = new HashSet<>();

    public Wolf() {
    }

    public Wolf(@Pattern(regexp = "^([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)$", message = "Enter a correct GSP coordinate, please.") @NotEmpty(message = "Enter a correct GSP coordinate, please.") @NotNull String location) {
        this.location = location;
    }

    public Wolf(long id, String name, Gender gender, Date birthDate, String location) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.location = location;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Wolf))
            return false;
        Wolf wolf = (Wolf) o;
        return Objects.equals(this.id, wolf.id) && Objects.equals(this.name, wolf.name)
                && Objects.equals(this.name, wolf.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.location);
    }

    @Override
    public String toString() {
        return "Wolf{" + "id=" + this.id + ", name='" + this.name + '\'' + ", location='" + this.location + '\'' + '}';
    }
}
