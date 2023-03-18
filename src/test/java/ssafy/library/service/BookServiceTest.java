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

}