package ru.itfb.it7.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
