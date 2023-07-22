package org.soneech.util;

import org.soneech.dao.PersonDao;
import org.soneech.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> foundPerson = personDao.findByFullName(person.getFullName());
        if (foundPerson.isPresent() && foundPerson.get().getId() != person.getId()) {
            errors.rejectValue("fullName", "500", "Читатель с таким именем уже существует");
        }
    }
}
