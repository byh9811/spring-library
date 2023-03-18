package ssafy.library.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.BookInfo;
import ssafy.library.domain.Category;
import ssafy.library.exception.DataOutOfRangeException;
import ssafy.library.exception.DuplicateException;
import ssafy.library.exception.NullExistException;
import ssafy.library.repository.BookInfoRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookInfoServiceTest {

    @Autowired
    BookInfoService bookInfoService;

    @Autowired
    BookInfoRepository bookInfoRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 도서정보등록_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());

        em.persist(category);

        // when
        String savedIsbn = bookInfoService.add(bookInfo);
        BookInfo savedBookInfo = bookInfoRepository.findById(savedIsbn);

        // then
        assertThat(bookInfo).isEqualTo(savedBookInfo);
    }

    @Test
    public void 도서정보등록_실패_널입력() throws Exception {

        // given
        Category category = new Category(null, "문학");
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, null, "자바의 정석", "도우출판", LocalDateTime.now());

        em.persist(category);

        // when & then
        assertThrows(NullExistException.class, () -> {
            bookInfoService.add(bookInfo);
        });

    }

    @Test
    public void 도서정보등록_실패_입력값() throws Exception {

        // given
        Category category = new Category(null, "문학");
        BookInfo bookInfo = new BookInfo("1".repeat(12), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());

        em.persist(category);

        // when & then
        assertThrows(DataOutOfRangeException.class, () -> {
            bookInfoService.add(bookInfo);
        });

    }

    @Test
    public void 도서정보등록_실패_ISBN중복() throws Exception {

        // given
        Category category = new Category(null, "문학");
        BookInfo bookInfo1 = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        BookInfo bookInfo2 = new BookInfo("1".repeat(13), category, "남궁성", "파이썬의 정석", "도우출판", LocalDateTime.now());

        em.persist(category);

        // when & then
        assertThrows(DuplicateException.class, () -> {
            bookInfoService.add(bookInfo1);
            bookInfoService.add(bookInfo2);
        });

    }

    @Test
    public void 도서정보등록_실패_이름중복() throws Exception {

        // given
        Category category = new Category(null, "교육");

        BookInfo bookInfo1 = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        BookInfo bookInfo2 = new BookInfo("2".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());

        em.persist(category);

        // when & then
        assertThrows(DuplicateException.class, () -> {
            bookInfoService.add(bookInfo1);
            bookInfoService.add(bookInfo2);
        });

    }

}