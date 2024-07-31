package ru.itfb.it7.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
