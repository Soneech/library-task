package org.soneech.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    private int id;

    @NotEmpty(message = "Укажите название книги")
    @Size(min = 2, max = 100, message = "Название должно содержать от 2 до 100 символов")
    private String title;

    @NotEmpty(message = "Укажите автора")
    @Size(min = 2, max = 200, message = "Имя автора должно содержать от 2 до 200 символов")
    private String author;

    @Min(value = 1500, message = "Год написания должен быть не меньше, чем 1500")
    private int year;
}
