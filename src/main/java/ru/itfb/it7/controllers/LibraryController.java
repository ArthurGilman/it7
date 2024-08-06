package ru.itfb.it7.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itfb.it7.dto.request.create.BookLendingCreateRequest;
import ru.itfb.it7.dto.request.create.ReaderCreateRequest;
import ru.itfb.it7.dto.request.create.ReaderTicketCreateRequest;
import ru.itfb.it7.dto.request.update.BookLendingUpdateRequest;
import ru.itfb.it7.dto.request.update.ReaderUpdateRequest;
import ru.itfb.it7.model.BookLending;
import ru.itfb.it7.model.Reader;
import ru.itfb.it7.service.data_jdbc.LibraryService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService service;

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


}
