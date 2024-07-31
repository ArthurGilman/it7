package ru.itfb.it7.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.BookDisposal;

@Repository
public interface BookDisposalRepository extends CrudRepository<BookDisposal, Long> {
}
