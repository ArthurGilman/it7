package ru.itfb.it7.repositories.dataJDBC;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.Reader;
import ru.itfb.it7.projections.ReaderProjection;

import java.time.LocalDate;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {

    ReaderProjection findReaderByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
}
