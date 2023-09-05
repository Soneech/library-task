package org.soneech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Укажите название книги")
    @Size(min = 2, max = 100, message = "Название должно содержать от 2 до 100 символов")
    private String title;

    @NotEmpty(message = "Укажите автора")
    @Size(min = 2, max = 200, message = "Имя автора должно содержать от 2 до 200 символов")
    private String author;

    @Min(value = 1500, message = "Год написания должен быть не меньше, чем 1500")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Override
    public String toString() {
        return String.format("Book{id=%d, title=%s, author=%s, year=%d}", id, title, author, year);
    }
}
