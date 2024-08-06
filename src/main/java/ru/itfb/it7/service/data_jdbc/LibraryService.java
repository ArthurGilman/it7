package ru.itfb.it7.service.data_jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.itfb.it7.dto.request.ReaderRequest;
import ru.itfb.it7.dto.request.create.*;
import ru.itfb.it7.dto.request.update.BookCopyUpdateRequest;
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
import ru.itfb.it7.repositories.dataJDBC.RackRepository;
import ru.itfb.it7.repositories.dataJDBC.ReaderRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryService {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final BookLendingRepository bookLendingRepository;
    private final RackRepository rackRepository;
    private final LibraryJDBCTemplateRepository templateRepository;
    private final TransactionTemplate transactionTemplate;

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
    public Reader createNewReaderTicketByReaderId(Long id) {
        Reader r = readerRepository.findById(id).orElseThrow(() -> new ReaderNotExist("Невозможно создать билет, так как читателя не существует"));
        ReaderTicket rt = ReaderTicket.builder()
                .issueDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusDays(365))
                .status("active")
                .build();
        r.setReaderTicket(rt);
        return readerRepository.save(r);
    }

    // TODO: java docs
    public Reader blockReaderTicketByReaderId (Long id) {
        Reader r = readerRepository.findById(id).orElseThrow(() -> new ReaderNotExist("Невозможно заблокировать билет, так как читателя не существует"));
        r.getReaderTicket().setStatus("block");
        return readerRepository.save(r);
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
     * Создание новых "справочных" записей: автор/книга/стеллаж(со всеми полками)
     * Заведение новых книг
     */
    public Iterable<Book> createBook(List<BookCreateRequest> request) {
        List<Book> allBooks = new ArrayList<>();
        transactionTemplate.execute(status -> {
            try {
                for (BookCreateRequest req : request) {
                    Book b = Book.builder()
                            .title(req.getTitle())
                            .isbn(req.getIsbn())
                            .categories(req.getCategories())
                            .authors(req.getAuthors())
                            .build();
                    allBooks.add(b);
                }
                return bookRepository.saveAll(allBooks);
            } catch (Exception ex) {
                status.setRollbackOnly();
                throw ex;
            }
        });
        return allBooks;
    }

    /**
     * TODO: заведение новых стеллажей
     */
    public List<Rack> createRacks(List<RackCreateRequest> request) {
        List<Rack> racks = new ArrayList<>();
        for (RackCreateRequest req : request) {
            Rack rack = Rack.builder()
                    .location(req.getLocation())
                    .shelves(new HashSet<>())
                    .build();
            racks.add(rack);
        }
        rackRepository.saveAll(racks);
        return racks;
    }


    /**
     * создание нового экземляра книги
     * @return
     */
    public Book createBookCopy(BookCopyCreateRequest request) throws BookNotExist {
        Book b = bookRepository.findById(request.getBookId()).orElseThrow(() -> new BookNotExist("Такой книги не существует"));
        BookCopy copy = BookCopy.builder()
                .shelfId(request.getShelfId())
                .status(request.getStatus())
                .build();
        b.getBookCopies().add(copy);
        return bookRepository.save(b);
    }
    /**
     * TODO: пакетное создание нескольких экземляров одной и той же книги
     */

    public Book createBookCopies(List<BookCopyCreateRequest> request) {
        Book b = bookRepository.findById(request.get(0).getBookId())
                .orElseThrow(() -> new BookNotExist("Такой книги не существует"));
        Set<BookCopy> copies = b.getBookCopies();
        for (BookCopyCreateRequest req : request) {
            if (!req.getBookId().equals(b.getId())) {
                throw new UnsupportedOperationException("Запрещенено пакетное создание экземялров для разных книг");
            }
            BookCopy copy = BookCopy.builder()
                    .shelfId(req.getShelfId())
                    .status(req.getStatus())
                    .build();
            copies.add(copy);
        }
        return bookRepository.save(b);
    }


    /**
     * TODO: обновление экземляра книги
     */
    public BookCopy updateBookCopy(BookCopyUpdateRequest request) {
        Book b = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BookNotExist("Книги, к которой относится экземпляр книги нет, в базе данных"));

        BookCopy bookCopy = b.getBookCopies().stream()
                .filter(c -> c.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new BookNotExist("Экзмепляра с id " + request.getId() + " нет в базе данных"));
        bookCopy.setShelfId(request.getShelfId());
        bookCopy.setStatus(request.getStatus());
        return bookCopy;
    }
}
