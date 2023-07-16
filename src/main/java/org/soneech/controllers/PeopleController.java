package org.soneech.controllers;

import org.soneech.dao.PersonDao;
import org.soneech.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String peoplePage(Model model) {
        List<Person> personList = personDao.findAll();
        personList.forEach(System.out::println);
        model.addAttribute("people", personList);
        return "people/people_page";
    }
}
