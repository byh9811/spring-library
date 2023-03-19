package ssafy.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.*;
import ssafy.library.repository.BookRepository;
import ssafy.library.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 예약등록_성공() throws Exception {

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
        Long reservedId = reservationService.reserve(memberId, bookId);
        Reservation reservation = em.find(Reservation.class, reservedId);

        // then
        assertEquals(reservedId, reservation.getId());
        assertEquals(member, reservation.getMember());
        assertEquals(book, reservation.getBook());
        assertEquals(BookStatus.RESERVATION, book.getStatus());

    }

    @Test
    public void 예약전체조회_성공() throws Exception {

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

        Long reservedId1 = reservationService.reserve(memberId, bookId);
        em.find(Reservation.class, reservedId1);
        Long reservedId2 = reservationService.reserve(memberId, bookId);
        em.find(Reservation.class, reservedId2);

        // when
        List<Reservation> reservationList = reservationService.findAllReservation();

        // then
        assertEquals(2, reservationList.size());

    }

    @Test
    public void 내예약조회_성공() throws Exception {

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

        Long reservedId1 = reservationService.reserve(memberId, bookId);
        em.find(Reservation.class, reservedId1);
        Long reservedId2 = reservationService.reserve(memberId, bookId);
        em.find(Reservation.class, reservedId2);

        // when
        List<Reservation> reservationList = reservationService.findMyReservation(memberId);

        // then
        assertEquals(2, reservationList.size());

    }

    @Test
    public void 예약조회_성공() throws Exception {

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

        Long reservedId = reservationService.reserve(memberId, bookId);
        Reservation reservation = em.find(Reservation.class, reservedId);

        // when
        Reservation savedReservation = reservationService.findReservation(reservedId);

        // then
        assertEquals(savedReservation, reservation);

    }
}