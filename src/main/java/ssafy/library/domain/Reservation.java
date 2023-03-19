package ssafy.library.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @NotNull
    private Book book;

    @NotNull
    private LocalDateTime reservationDate;

    public static Reservation createReservation(Member member, Book book) {
        Reservation reservation = new Reservation();
        reservation.member = member;
        book.setStatus(BookStatus.RESERVATION);
        reservation.book = book;
        reservation.reservationDate = LocalDateTime.now();
        return reservation;
    }

    public void cancelReservation() {
        book.setStatus(BookStatus.NONE);
    }
}
