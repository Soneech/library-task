package org.soneech.controllers;

import jakarta.validation.Valid;
import org.soneech.dao.PersonDao;
import org.soneech.models.Person;
import org.soneech.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String peoplePage(Model model) {
        List<Person> personList = personDao.findAll();
        model.addAttribute("people", personList);
        return "people/people_page";
    }

    @GetMapping("/new")
    public String creationPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable int id, Model model) {
        model.addAttribute("person", personDao.findById(id));
        model.addAttribute("books", personDao.findBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.findById(id));
        return "people/edit";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDao.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id,
                             @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
