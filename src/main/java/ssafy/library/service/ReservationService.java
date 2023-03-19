package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.library.domain.Book;
import ssafy.library.domain.Member;
import ssafy.library.domain.Reservation;
import ssafy.library.repository.BookRepository;
import ssafy.library.repository.MemberRepository;
import ssafy.library.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    public Long reserve(Long memberId, String bookId) {
        Member member = memberRepository.findById(memberId);
        Book book = bookRepository.findById(bookId);
        return reservationRepository.save(Reservation.createReservation(member, book));
    }

    public List<Reservation> findAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation findReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> findMyReservation(Long memberId) {
        Member member = memberRepository.findById(memberId);
        return reservationRepository.findByMemberId(member);
    }
}
