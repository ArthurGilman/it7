package ru.itfb.it7.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
