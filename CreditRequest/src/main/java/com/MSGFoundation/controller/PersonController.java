package com.MSGFoundation.controller;

import com.MSGFoundation.dto.CoupleDTO;
import com.MSGFoundation.dto.CreditInfoDTO;
import com.MSGFoundation.model.Person;
import com.MSGFoundation.service.impl.CoupleServiceImpl;
import com.MSGFoundation.service.impl.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private final PersonServiceImpl personService;
    private final CoupleServiceImpl coupleService;

    @GetMapping("/")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable String id) {
        return personService.getPersonById(id);
    }

    @PostMapping("/create")
    public String createPerson(@RequestBody CreditInfoDTO creditInfoDTO) {
        List<Person> people = creditInfoDTO.getPeople();

        personService.createPerson(people.get(0));
        personService.createPerson(people.get(1));

        CoupleDTO couple = new CoupleDTO();
        couple.setPartner1Id(people.get(0).getId());
        couple.setPartner2Id(people.get(1).getId());
        coupleService.createCouple(couple);

        return "People has been created successfully";
    }

    @PutMapping("/update/{id}")
    public Person updatePerson(@PathVariable String id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
    }
}


