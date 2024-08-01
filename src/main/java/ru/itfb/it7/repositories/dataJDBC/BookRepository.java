package ru.itfb.it7.repositories.dataJDBC;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("select b.id, b.title, b.isbn from book b join book_copy bc on b.id = bc.id where b.id = :copyId")
    Optional<Book> findBookByBookLendingCopyId(Long copyId);
}
