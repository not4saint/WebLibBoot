package com.artem.weblibrary.WebLibBoot.dao;//package com.weblib.dao;
//
//import com.weblib.models.Book;
//import com.weblib.models.Person;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class PersonDAO {
//    private final JdbcTemplate jdbcTemplate;
//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Person> selectAllPeople() {
//        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//
//    }
//    public Person selectPerson(int id) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
//    }
//
//    public void savePerson(Person person) {
//        jdbcTemplate.update("INSERT INTO Person(name, date) VALUES (?, ?)",
//                person.getName(), person.getDate());
//    }
//
//    public void updatePerson(int id, Person person) {
//        jdbcTemplate.update("UPDATE Person SET name=?, date=? WHERE id=?",
//                person.getName(), person.getDate(), id);
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
//    }
//    public List<Book> getBooksByPersonId(int id) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{id},
//                new BeanPropertyRowMapper<>(Book.class));
//    }
//
//    /////////////////////////////////////
//    //////проверка на уникальность имени/
//    /////////////////////////////////////
//    public Optional<Person> checkPersonName(String name) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
//    }
//}
