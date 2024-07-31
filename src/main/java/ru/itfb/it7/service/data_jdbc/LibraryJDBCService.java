package ru.itfb.it7.service.data_jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itfb.it7.dto.request.ReaderRequest;
import ru.itfb.it7.dto.request.create.BookLendingCreateRequest;
import ru.itfb.it7.dto.request.create.ReaderCreateRequest;
import ru.itfb.it7.dto.request.update.BookLendingUpdateRequest;
import ru.itfb.it7.dto.request.update.ReaderUpdateRequest;
import ru.itfb.it7.exception.BookNotExist;
import ru.itfb.it7.exception.ReaderAlreadyExist;
import ru.itfb.it7.exception.ReaderNotExist;
import ru.itfb.it7.model.*;
import ru.itfb.it7.projections.ReaderProjection;
import ru.itfb.it7.repositories.BookLendingRepository;
import ru.itfb.it7.repositories.BookRepository;
import ru.itfb.it7.repositories.ReaderRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryJDBCService {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final BookLendingRepository bookLendingRepository;

    /**
     * Поиск читателя и его активного читательского билета по ФИО и ДР (истекшие не должны возвращаться)
     *
     * @param request - dto c тремя полнями (firstName, lastName, birthDate)
     *
     * @return Проекция читателя
     */
    public ReaderProjection findReaderWithTicket(ReaderRequest request) {
        return readerRepository.findReaderByFirstNameAndLastNameAndBirthDate(
                request.getFirstName(),
                request.getLastName(),
                request.getBirthDate()
        );
    }

    /**
     * Заведение нового читателя и одновременно его первого читательского билета
     *
     * @param request
     * @return
     */

    public Reader createReaderWithTicket(ReaderCreateRequest request) {
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
    // TODO: java docs
    public Reader createNewReaderTicket(ReaderUpdateRequest request) {
        Reader r = readerRepository.findById(request.getId()).orElseThrow(() -> new ReaderNotExist("Невозможно обновить билет, так как читателя не существует"));
        ReaderTicket rt = ReaderTicket.builder()
                .issueDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusDays(365))
                .status("active")
                .build();
        r.setReaderTicket(rt);
        return readerRepository.save(r);
    }

    // TODO: java docs
    public Reader blockReaderTicket (ReaderUpdateRequest request) {
        Reader r = readerRepository.findById(request.getId()).orElseThrow(() -> new ReaderNotExist("Невозможно заблокировать билет, так как читателя не существует"));
        r.getReaderTicket().setStatus("block");
        return r;
    }

    /**
     * given book
     * @param request
     * @return
     */
    public BookLending createBookLending(BookLendingCreateRequest request) {
//        bookRepository.findBookByBookLendingCopyId(request.getCopyId()).orElseThrow(() -> new BookNotExist("Нет такой книги"));
        BookLending bookLending = bookLendingRepository.findBookLendingByCopyId(request.getCopyId()).orElseThrow(() -> new BookNotExist("no book"));
        if (bookLending.getReturnDate() == null) {
            throw new BookNotExist("Книги еще не возвращена");
        } else if (!(request.getLendDate().isAfter(bookLending.getReturnDate()))) {
            throw new BookNotExist("Книги еще не возвращена");
        }
        BookLending bl = BookLending.builder()
                .copyId(request.getCopyId())
                .dueDate(request.getDueDate())
                .lendDate(request.getLendDate())
                .build();
        return bookLendingRepository.save(bl);
    }

    /**
     * return book
     * @param request
     * @return
     */
    public BookLending updateBookLending(BookLendingUpdateRequest request) {
        Book book = bookRepository.findBookByBookLendingCopyId(request.getCopyId()).orElseThrow(() -> new BookNotExist("Нет такой книги"));
        BookLending bl = BookLending.builder()
                .id(request.getId())
                .copyId(request.getCopyId())
                .dueDate(request.getDueDate())
                .lendDate(request.getLendDate())
                .returnDate(request.getReturnDate())
                .build();
        return bookLendingRepository.save(bl);
    }



}
