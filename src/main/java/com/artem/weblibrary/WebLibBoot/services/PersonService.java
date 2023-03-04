package com.artem.weblibrary.WebLibBoot.services;

import com.artem.weblibrary.WebLibBoot.models.Book;
import com.artem.weblibrary.WebLibBoot.models.Person;
import com.artem.weblibrary.WebLibBoot.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> selectAllPeople() {
        return peopleRepository.findAll();
    }

    public Person selectOnePerson(int id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void deletePersonById(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);

        if (optionalPerson.isPresent()) {
            Hibernate.initialize(optionalPerson.get().getBooks());
            return optionalPerson.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Person> checkByUniqueName(String name) {
        return peopleRepository.findByName(name).stream().findAny();
    }
}
