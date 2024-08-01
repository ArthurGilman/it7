package ru.itfb.it7.service.data_jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itfb.it7.dto.request.ReaderRequest;
import ru.itfb.it7.dto.request.create.BookCopyCreateRequest;
import ru.itfb.it7.dto.request.create.BookDisposalRequest;
import ru.itfb.it7.dto.request.create.BookLendingCreateRequest;
import ru.itfb.it7.dto.request.create.ReaderCreateRequest;
import ru.itfb.it7.dto.request.update.BookLendingUpdateRequest;
import ru.itfb.it7.dto.request.update.ReaderUpdateRequest;
import ru.itfb.it7.exception.BookAlreadyWrittenOff;
import ru.itfb.it7.exception.BookNotExist;
import ru.itfb.it7.exception.ReaderNotExist;
import ru.itfb.it7.model.*;
import ru.itfb.it7.projections.ReaderProjection;
import ru.itfb.it7.repositories.JDBC.LibraryJDBCTemplateRepository;
import ru.itfb.it7.repositories.dataJDBC.BookLendingRepository;
import ru.itfb.it7.repositories.dataJDBC.BookRepository;
import ru.itfb.it7.repositories.dataJDBC.ReaderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryJDBCService {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final BookLendingRepository bookLendingRepository;
    private final LibraryJDBCTemplateRepository templateRepository;

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
        bookRepository.findBookByBookLendingCopyId(request.getCopyId()).orElseThrow(() -> new BookNotExist("Нет такой книги"));

        Optional<BookLending> bookLending = bookLendingRepository.findBookLendingByCopyId(request.getCopyId());
        if (bookLending.isPresent()) {
            BookLending bl = bookLending.get();
            if (bl.getReturnDate() == null) {
                throw new BookNotExist("Книги еще не возвращена");
            } else if (!(request.getLendDate().isAfter(bl.getReturnDate()))) {
                throw new BookNotExist("Книги еще не возвращена");
            }
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
        bookRepository.findBookByBookLendingCopyId(request.getCopyId()).orElseThrow(() -> new BookNotExist("Нет такой книги"));
        BookLending bl = BookLending.builder()
                .id(request.getId())
                .copyId(request.getCopyId())
                .dueDate(request.getDueDate())
                .lendDate(request.getLendDate())
                .returnDate(request.getReturnDate())
                .build();
        return bookLendingRepository.save(bl);
    }

    /**
     * Фиксация утери книги
     * @param request
     * @return
     * @throws EmptyResultDataAccessException
     */
    public BookDisposal recordLossOfBook(BookDisposalRequest request) throws EmptyResultDataAccessException {
        List<BookDisposal> bookDisposalList = templateRepository.findAllBookDisposalByCopyId(request.getCopyId());
        if (!bookDisposalList.isEmpty()) {
            throw new BookAlreadyWrittenOff();
        }
        templateRepository.findBookCopyById(request.getCopyId());
        LocalDate disposalDate = LocalDate.now();
        Long id = templateRepository.createBookDisposal(request.getCopyId(), disposalDate, request.getReason());
        return BookDisposal.builder()
                .id(id)
                .disposalDate(disposalDate)
                .reason(request.getReason())
                .copyId(request.getCopyId())
                .build();
    }

    /**
     * Фиксация списания книги
     * @param request
     * @return
     */
    public BookDisposal bookWriteOff(BookDisposalRequest request) {
        List<BookDisposal> bookDisposalList = templateRepository.findAllBookDisposalByCopyId(request.getCopyId());
        if (!bookDisposalList.isEmpty()) {
            throw new BookAlreadyWrittenOff();
        }
        templateRepository.findBookCopyById(request.getCopyId());
        LocalDate disposalDate = LocalDate.now();
        Long id = templateRepository.createBookDisposal(request.getCopyId(), disposalDate, request.getReason());
        return BookDisposal.builder()
                .id(id)
                .disposalDate(disposalDate)
                .reason(request.getReason())
                .copyId(request.getCopyId())
                .build();
    }

    /**
     * создание нового экземляра книги
     * @return
     */
    public void createBookCopy(BookCopyCreateRequest request) throws BookNotExist {
        Book b = bookRepository.findById(request.getBookId()).orElseThrow(() -> new BookNotExist("Такой книги не существует"));
        BookCopy copy = BookCopy.builder()
                .shelfId(request.getShelfId())
                .status(request.getStatus())
                .build();
        b.getBookCopies().add(copy);
        bookRepository.save(b);
    }



}
