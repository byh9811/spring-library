package ssafy.library.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.Book;
import ssafy.library.domain.BookInfo;
import ssafy.library.domain.BookStatus;
import ssafy.library.domain.Category;
import ssafy.library.repository.dto.BookSearch;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    EntityManager em;

    @Test
    public void 도서등록_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book = new Book("abcde12345", bookInfo, BookStatus.NONE);

        // when
        String id = bookService.add(book);
        Book savedBook = em.find(Book.class, id);

        // then
        assertThat(savedBook).isEqualTo(book);
    }

    @Test
    public void 도서전체조회_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book1 = new Book("abcde12345", bookInfo, BookStatus.NONE);
        Book book2 = new Book("abcde12346", bookInfo, BookStatus.NONE);
        Book book3 = new Book("abcde12347", bookInfo, BookStatus.NONE);
        String id1 = bookService.add(book1);
        String id2 = bookService.add(book2);
        String id3 = bookService.add(book3);

        // when
        List<Book> bookList = bookService.findAll();

        // then
        assertThat(bookList.size()).isEqualTo(3);
    }

    @Test
    public void 도서단일조건조회_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book1 = new Book("abcde12345", bookInfo, BookStatus.NONE);
        Book book2 = new Book("abcde12346", bookInfo, BookStatus.RESERVATION);
        Book book3 = new Book("abcde12347", bookInfo, BookStatus.LOAN);
        bookService.add(book1);
        bookService.add(book2);
        bookService.add(book3);
        BookSearch bookSearch1 = new BookSearch();
        BookSearch bookSearch2 = new BookSearch();
        BookSearch bookSearch3 = new BookSearch();
        bookSearch1.setBook_id("abcde12345");
        bookSearch2.setIsbn("1".repeat(13));
        bookSearch3.setStatus(BookStatus.LOAN);
        // when
        List<Book> bookList1 = bookService.find(bookSearch1);
        List<Book> bookList2 = bookService.find(bookSearch2);
        List<Book> bookList3 = bookService.find(bookSearch3);

        // then
        assertThat(bookList1.size()).isEqualTo(1);
        assertThat(bookList2.size()).isEqualTo(3);
        assertThat(bookList3.size()).isEqualTo(1);
    }

    @Test
    public void 도서다중조건조회_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book1 = new Book("abcde12345", bookInfo, BookStatus.NONE);
        Book book2 = new Book("abcde12346", bookInfo, BookStatus.NONE);
        Book book3 = new Book("abcde12347", bookInfo, BookStatus.LOAN);
        bookService.add(book1);
        bookService.add(book2);
        bookService.add(book3);
        BookSearch bookSearch = new BookSearch();
        bookSearch.setIsbn("1".repeat(13));
        bookSearch.setStatus(BookStatus.NONE);

        // when
        List<Book> bookList = bookService.find(bookSearch);

        // then
        assertThat(bookList.size()).isEqualTo(2);
    }

    @Test
    public void 도서삭제_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book = new Book("abcde12345", bookInfo, BookStatus.NONE);
        String savedId = bookService.add(book);

        // when
        String removedId = bookService.delete("abcde12345");

        // then
        assertThat(savedId).isEqualTo(removedId);
    }
}