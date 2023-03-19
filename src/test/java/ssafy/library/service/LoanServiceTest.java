package ssafy.library.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.*;
import ssafy.library.exception.MaxLoanException;
import ssafy.library.exception.ReservationException;
import ssafy.library.repository.BookInfoRepository;
import ssafy.library.repository.BookRepository;
import ssafy.library.repository.CategoryRepository;
import ssafy.library.repository.MemberRepository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoanServiceTest {

    @Autowired
    LoanService loanService;

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookInfoRepository bookInfoRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void 도서대출_성공() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);
        Long memberId = memberRepository.save(member);

        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book = new Book("abcde12345", bookInfo, BookStatus.NONE);
        String bookId = bookRepository.save(book);

        // when
        Long loan_id = loanService.checkout(memberId, bookId);
        Loan savedLoan = em.find(Loan.class, loan_id);

        // then
        assertEquals(loan_id, savedLoan.getId());
        assertEquals(LoanStatus.LOAN, savedLoan.getStatus());
        assertEquals(member, savedLoan.getMember());
        assertEquals(book, savedLoan.getBook());

    }

    @Test
    public void 도서대출_실패_5권초과() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);
        Long memberId = memberRepository.save(member);

        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book = new Book("abcde12345", bookInfo, BookStatus.NONE);
        Book book2 = new Book("abcde12346", bookInfo, BookStatus.NONE);
        Book book3 = new Book("abcde12347", bookInfo, BookStatus.NONE);
        Book book4 = new Book("abcde12348", bookInfo, BookStatus.NONE);
        Book book5 = new Book("abcde12349", bookInfo, BookStatus.NONE);
        Book book6 = new Book("abcde12340", bookInfo, BookStatus.NONE);
        String bookId = bookRepository.save(book);
        String bookId2 = bookRepository.save(book2);
        String bookId3 = bookRepository.save(book3);
        String bookId4 = bookRepository.save(book4);
        String bookId5 = bookRepository.save(book5);
        String bookId6 = bookRepository.save(book6);
        loanService.checkout(memberId, bookId);
        loanService.checkout(memberId, bookId2);
        loanService.checkout(memberId, bookId3);
        loanService.checkout(memberId, bookId4);
        loanService.checkout(memberId, bookId5);

        // when & then
        assertThrows(MaxLoanException.class, () -> {
            loanService.checkout(memberId, bookId6);
        });

    }

    @Test
    public void 도서대출_실패_예약도서() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);
        Long memberId = memberRepository.save(member);

        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book = new Book("abcde12345", bookInfo, BookStatus.RESERVATION);
        String bookId = bookRepository.save(book);

        // when & then
        assertThrows(ReservationException.class, () -> {
            loanService.checkout(memberId, bookId);
        });

    }

    @Test
    public void 도서대출_반납() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);
        Long memberId = memberRepository.save(member);

        Category category = new Category(null, "문학");
        em.persist(category);
        BookInfo bookInfo = new BookInfo("1".repeat(13), category, "남궁성", "자바의 정석", "도우출판", LocalDateTime.now());
        em.persist(bookInfo);
        Book book = new Book("abcde12345", bookInfo, BookStatus.NONE);
        String bookId = bookRepository.save(book);
        Long loan_id = loanService.checkout(memberId, bookId);
        em.find(Loan.class, loan_id);

        // when
        Long returnedId = loanService.returnBook(loan_id);

        // then
        assertEquals(returnedId, loan_id);

    }
}