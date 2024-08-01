package ru.itfb.it7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itfb.it7.model.Author;
import ru.itfb.it7.model.Book;
import ru.itfb.it7.repositories.dataJDBC.BookRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJdbcTest
@AutoConfigureTestDatabase
class It7ApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Test
	void createBook() {
		Book book = Book.builder()
				.isbn("2134")
				.title("title")
				.build();
		book.addAuthor(Author.builder()
				.firstName("name")
				.lastName("lastName")
				.build());
		bookRepository.save(book);
		List<Book> allBooks = new ArrayList<>();
		bookRepository.findAll().forEach(allBooks::add);
		for (Book b : allBooks) {
			System.out.println(b);
		}
		Assertions.assertEquals(allBooks.size(), 1);
	}

}
