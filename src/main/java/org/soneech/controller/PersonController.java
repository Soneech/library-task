package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.model.Book;
import org.soneech.model.Person;
import org.soneech.service.PersonService;
import org.soneech.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String peoplePage(Model model) {
        List<Person> personList = personService.findAll();
        model.addAttribute("people", personList);
        return "people/people_page";
    }

    @GetMapping("/new")
    public String creationPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    public void setErrorMessage(Model model) {
        model.addAttribute("error_message", "Нет такого читателя!");
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable int id, Model model) {
        Optional<Person> person = personService.findById(id);

        if (person.isPresent()) {
            model.addAttribute("person", person.get());

            List<Book> books = personService.getBooksByPersonId(id);
            model.addAttribute("books", books);
        }
        else {
            setErrorMessage(model);
        }
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personService.findById(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
        }
        else {
            setErrorMessage(model);
        }
        return "people/edit";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id,
                             @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
