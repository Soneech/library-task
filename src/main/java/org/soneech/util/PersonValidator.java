package org.soneech.util;

import org.soneech.model.Person;
import org.soneech.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> foundPerson = personService.findByFullName(person.getFullName());
        if (foundPerson.isPresent() && foundPerson.get().getId() != person.getId()) {
            errors.rejectValue("fullName", "500", "Читатель с таким именем уже существует");
        }
    }
}
