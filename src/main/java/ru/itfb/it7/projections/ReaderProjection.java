package ru.itfb.it7.projections;

import ru.itfb.it7.model.ReaderTicket;

import java.time.LocalDate;


public interface ReaderProjection {
    String getFirstName();
    String getLastName();
    ReaderTicket getReaderTicket();
}
