package ru.itfb.it7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.itfb.it7.model.*;
import ru.itfb.it7.repositories.AuthorRepository;
import ru.itfb.it7.repositories.BookRepository;
import ru.itfb.it7.repositories.RackRepository;
import ru.itfb.it7.repositories.ReaderRepository;
import ru.itfb.it7.projections.ReaderProjection;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class It7Application {

	private static AtomicLong bookAtomic = new AtomicLong(0);
	private static AtomicLong authorAtomic = new AtomicLong(0);

	public static void main(String[] args) {
		SpringApplication.run(It7Application.class, args);
	}

	@Bean
	CommandLineRunner init(BookRepository bookRepository,
						   AuthorRepository authorRepository,
						   RackRepository rackRepository, ReaderRepository readerRepository) {
		return args -> {
			readerRepository.deleteAll();
			Shelf shelf = Shelf.builder().capacity(10).position("middle").build();
			Rack rack = Rack.builder().location("location1").shelves(Set.of(shelf)).build();
			rackRepository.save(rack);
//			readerRepository.deleteAll();
			Book book = Book.builder()
					.isbn("2134")
					.title("title")
					.authors(new HashSet<>())
					.bookCopies(new ArrayList<>())
					.categories(new HashSet<>())
					.build();
			book.getBookCopies().add(BookCopy.builder()
					.shelfId(1L)
					.status("good")
					.build());
			Author author = Author.builder()
					.firstName("name")
					.lastName("lastName")
					.build();
			authorRepository.save(author);
			book.addAuthor(author);
			bookRepository.save(book);
			Book b = bookRepository.findBookByBookLendingCopyId(3L).orElse(null);
			System.out.println(b);


//			BookCopy bookCopy1 = BookCopy.builder().shelfId(shelf.getId()).status("fine").build();
//			BookCopy bookCopy2 = BookCopy.builder().shelfId(shelf.getId()).status("normal").build();
//			book.addBookCopy(bookCopy1);
//			book.addBookCopy(bookCopy2);
//			bookRepository.save(book);
//			List<Book> allBooks = new ArrayList<>();
//			bookRepository.findAll().forEach(allBooks::add);
//			for (Book b : allBooks) {
//				System.out.println(b.toString());
//			}
//
//			bookRepository.deleteAll();
////			authorRepository.findAll().forEach(System.out::println);
//			authorRepository.deleteAll();
		};
	}

//	@Bean
//	public ApplicationListener<?> idSetting() {
//		return (ApplicationListener<BeforeConvertEvent>) event -> {
//			if (event.getEntity() instanceof Book) {
//
//				Long id = bookAtomic.incrementAndGet();
//				((Book) event.getEntity()).setId(id);
//			} else if (event.getEntity() instanceof Author) {
//				Long id = authorAtomic.incrementAndGet();
//				((Author) event.getEntity()).setId(id);
//			}
//		};
//	}



}
