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
//public class BookDAO {
//    private final JdbcTemplate jdbcTemplate;
//    @Autowired
//    public BookDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Book> selectAllBooks() {
//        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
//    }
//    public Book selectBook(int id) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id},
//                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
//    }
//
//    public void saveBook(Book book) {
//        jdbcTemplate.update("INSERT INTO Book(name, author, date) VALUES (?, ?, ?)",
//                book.getName(), book.getAuthor(), book.getDate());
//    }
//
//    public void updateBook(int id, Book book) {
//        jdbcTemplate.update("UPDATE Book SET name=?, author=?, date=? WHERE id=?",
//                book.getName(), book.getAuthor(), book.getDate(), id);
//    }
//
//    public Optional<Person> getBookOwner(int id) {
//        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id=?",
//                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
//    }
//
//    public void release(int id) {
//        jdbcTemplate.update("UPDATE Book Set person_id=NULL WHERE id=?", id);
//    }
//
//    public void assign(int id, Person person) {
//        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", person.getId(), id);
//    }
//
//    /////////////////////////////////////////////////
//    //////проверка на уникальность названия и автора/
//    /////////////////////////////////////////////////
//    public Optional<Book> checkBookNameAndAuthor(String name, String author) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE name=? and author=?", new Object[]{name, author},
//                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
//    }
//}
