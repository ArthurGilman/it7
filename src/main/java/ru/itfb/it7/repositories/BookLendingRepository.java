package ru.itfb.it7.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.BookCopy;
import ru.itfb.it7.model.BookLending;

import java.util.Optional;

@Repository
public interface BookLendingRepository extends CrudRepository<BookLending, Long> {
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("select bl.id, bl.copy_id, bl.ticket_id, bl.lend_date, bl.due_date, bl.return_date from book_copy bc join book_lending bl on bl.id = bc.id where bc.id = :copyId")
    Optional<BookLending> findBookLendingByCopyId(Long copyId);
}
