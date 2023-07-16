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
public class Person {
    private int id;

    @NotEmpty(message = "Укажите имя")
    @Size(min = 2, max = 200, message = "Имя должно содержать от 2 до 100 символов")
    private String fullName;

    @NotEmpty(message = "Укажите год рождения")
    @Min(value = 1910, message = "Год рождения должен быть не меньше, чем 1910")
    private int yearOfBirth;
}
