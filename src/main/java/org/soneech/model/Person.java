package org.soneech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Укажите имя")
    @Size(min = 2, max = 200, message = "Имя должно содержать от 2 до 100 символов")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1910, message = "Год рождения должен быть не меньше, чем 1910")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @Override
    public String toString() {
        return String.format("Person{id=%d, fullName=%s, yearOfBirth=%d}", id, fullName, yearOfBirth);
    }
}
