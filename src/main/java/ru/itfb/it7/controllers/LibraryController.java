package ru.itfb.it7.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itfb.it7.dto.request.ReaderRequest;
import ru.itfb.it7.dto.request.create.*;
import ru.itfb.it7.dto.request.update.BookCopyUpdateRequest;
import ru.itfb.it7.dto.request.update.BookLendingUpdateRequest;
import ru.itfb.it7.model.*;
import ru.itfb.it7.projections.ReaderProjection;
import ru.itfb.it7.service.data_jdbc.LibraryService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService service;

    public ResponseEntity<ReaderProjection> findReader(ReaderRequest request) {
        ReaderProjection r = service.findReaderWithTicket(request);
        return ResponseEntity.ok(r);
    }

    @PostMapping("/reader/create")
    public ResponseEntity<Reader> createReaderWtihTicket(@RequestBody ReaderCreateRequest request) {
        log.info("create reader");
        Reader r = service.createReaderWithTicket(request);
        return ResponseEntity.ok(r);
    }

    @PostMapping("/reader-ticket/create")
    public ResponseEntity<Reader> createReaderTicket(@RequestParam Long id) {
        log.info("create reader ticket");
        Reader r = service.createNewReaderTicketByReaderId(id);
        return ResponseEntity.ok(r);
    }

    @PutMapping("/reader-ticket/block")
    public ResponseEntity<Reader> blockReaderTicket(@RequestParam Long id) {
        log.info("block reader ticket");
        Reader r = service.blockReaderTicketByReaderId(id);
        return ResponseEntity.ok(r);
    }

    @PostMapping("/book-lending/create")
    public ResponseEntity<BookLending> createBookLending(@RequestBody BookLendingCreateRequest request) {
        log.info("create book lending");
        BookLending bl = service.createBookLending(request);
        return ResponseEntity.ok(bl);
    }

    @PutMapping("/book-lending/update")
    public ResponseEntity<BookLending> updateBookDisposal(@RequestBody BookLendingUpdateRequest request) {
        log.info("update book lending");
        BookLending bl = service.updateBookLending(request);
        return ResponseEntity.ok(bl);
    }

    @PostMapping("book-disposal/create")
    public ResponseEntity<BookDisposal> createBookDisposal(@RequestBody BookDisposalRequest request) {
        log.info("create book disposal");
        BookDisposal bd = service.bookWriteOff(request);
        return ResponseEntity.ok(bd);
    }

    @PostMapping("book/create")
    public ResponseEntity<Iterable<Book>> createBook(@RequestBody List<BookCreateRequest> request) {
        log.info("create books");
        Iterable<Book> books = service.createBook(request);
        return ResponseEntity.ok(books);
    }

    @PostMapping("rack/create")
    public ResponseEntity<List<Rack>> createRacks(@RequestBody List<RackCreateRequest> request) {
        log.info("create racks");
        List<Rack> racks = service.createRacks(request);
        return ResponseEntity.ok(racks);
    }

    @PostMapping("book-copy/create")
    public ResponseEntity<BookCopy> createBookCopy(@RequestBody BookCopyCreateRequest request) {
        log.info("create book copy");
        BookCopy bookCopy = service.createBookCopy(request);
        return ResponseEntity.ok(bookCopy);
    }

    @PostMapping("book-copy/create-all")
    public ResponseEntity<Set<BookCopy>> createBookCopies(@RequestBody List<BookCopyCreateRequest> request) {
        log.info("create book copies");
        Set<BookCopy> copies = service.createBookCopies(request);
        return ResponseEntity.ok(copies);
    }

    @PostMapping("book-copy/update")
    public ResponseEntity<BookCopy> updateBookCopy(@RequestBody BookCopyUpdateRequest request) {
        log.info("update book copy");
        BookCopy bookCopy = service.updateBookCopy(request);
        return ResponseEntity.ok(bookCopy);
    }

}
