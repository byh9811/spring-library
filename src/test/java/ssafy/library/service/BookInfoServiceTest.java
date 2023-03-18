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
import ssafy.library.repository.dto.BookInfoSearch;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    public void 도서정보검색_성공_전체검색() throws Exception {

        // given
        Category category = new Category(null, "교육");

        BookInfo bookInfo1 = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        BookInfo bookInfo2 = new BookInfo("2".repeat(13), category, "남궁성", "파이썬의 정석", "도우출판", LocalDateTime.now());

        em.persist(category);

        // when
        bookInfoService.add(bookInfo1);
        bookInfoService.add(bookInfo2);
        List<BookInfo> bookInfoList = bookInfoService.findAll();

        // then
        assertThat(bookInfoList.size()).isEqualTo(2);
    }

    @Test
    public void 도서정보검색_성공_조건검색() throws Exception {

        // given
        Category category1 = new Category(null, "교육");
        Category category2 = new Category(null, "문학");
        em.persist(category1);
        em.persist(category2);

        BookInfo bookInfo1 = new BookInfo("1".repeat(13), category1, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        BookInfo bookInfo2 = new BookInfo("2".repeat(13), category1, "남궁성", "파이썬의 정석", "도우출판", LocalDateTime.now());
        BookInfo bookInfo3 = new BookInfo("3".repeat(13), category2, "정지아", "아버지의 해방일지", "창비", LocalDateTime.now());
        BookInfo bookInfo4 = new BookInfo("4".repeat(13), category2, "정지아", "나의 아름다운 날들", "은행나무", LocalDateTime.now());

        bookInfoService.add(bookInfo1);
        bookInfoService.add(bookInfo2);
        bookInfoService.add(bookInfo3);
        bookInfoService.add(bookInfo4);

        BookInfoSearch bookInfoSearch = new BookInfoSearch();
//        bookInfoSearch.setAuthor("정지아");
        bookInfoSearch.setName("아버지의");

        // when
        List<BookInfo> bookInfoList = bookInfoService.find(bookInfoSearch);

        // then
        assertThat(bookInfoList.size()).isEqualTo(1);
    }
}