package ru.itfb.it7.service.data_jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itfb.it7.dto.request.ReaderRequest;
import ru.itfb.it7.exception.ReaderAlreadyExist;
import ru.itfb.it7.exception.ReaderNotExist;
import ru.itfb.it7.model.Reader;
import ru.itfb.it7.model.ReaderTicket;
import ru.itfb.it7.projections.ReaderProjection;
import ru.itfb.it7.repositories.ReaderRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryJDBCService {

    private final ReaderRepository readerRepository;

    /**
     * @param firstName - имя Читателя
     * @param lastName - фамилия Читателя
     * @param birthDate - дата рождения Читателя
     * @return ReaderProjection(firstName, lastName, ReaderTicket)
     */
    public ReaderProjection findReaderWithTicket(
            String firstName,
            String lastName,
            LocalDate birthDate
    ) {
        return readerRepository.findReaderByFirstNameAndLastNameAndBirthDate(
                firstName, lastName, birthDate);
    }

    public Reader createReaderWithTicket(ReaderRequest request) {
        Optional<Reader> optionalReader = readerRepository.findById(request.getId());
        if (optionalReader.isPresent()) {
            throw new ReaderAlreadyExist("Читатель с такий id существует");
        }
        ReaderTicket rt = ReaderTicket.builder()
                .issueDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusDays(365))
                .status("active")
                .build();
        Reader r = Reader.builder()
                .birthDate(request.getBirthDate())
                .ticketNumber(request.getTicketNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .readerTicket(rt)
                .build();
        return readerRepository.save(r);
    }

    public Reader createNewReaderTicket(ReaderRequest request) {
        Reader r = readerRepository.findById(request.getId()).orElseThrow(() -> new ReaderNotExist("Невозможно обновить билет, так как читателя не существует"));
        ReaderTicket rt = ReaderTicket.builder()
                .issueDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusDays(365))
                .status("active")
                .build();
        r.setReaderTicket(rt);
        return readerRepository.save(r);
    }

    public Reader blockReaderTicket (ReaderRequest request) {
        Reader r = readerRepository.findById(request.getId()).orElseThrow(() -> new ReaderNotExist("Невозможно заблокировать билет, так как читателя не существует"));
        r.getReaderTicket().setStatus("block");
        return r;
    }



}
