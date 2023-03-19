package ssafy.library.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.library.exception.MaxLoanException;
import ssafy.library.exception.ReservationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Loan {

    @Id @GeneratedValue
    @Column(name = "loan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @NotNull
    private Book book;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanStatus status;

    @NotNull
    private LocalDateTime loanedDate;

    @NotNull
    private LocalDateTime returnDate;

    public static Loan createLoan(Member member, Book book) {
        Loan loan = new Loan();
        loan.status = LoanStatus.LOAN;
        loan.loanedDate = LocalDateTime.now();
        loan.returnDate = LocalDateTime.now().plusDays(7);
        loan.setMember(member);
        loan.setBook(book);
        return loan;
    }

    private void setBook(Book book) {
        if (book.getStatus() != BookStatus.NONE) {
            throw new ReservationException("예약 도서는 대출이 불가합니다!");
        }

        this.book = book;
    }

    private void setMember(Member member) {
        if(member.getLoanList().size()>=5) {
            throw new MaxLoanException("도서 대출은 최대 5권까지 가능합니다!");
        }

        this.member = member;
        member.getLoanList().add(this);
    }

    public Long returnBook() {
        this.status = LoanStatus.RETURN;
        return this.id;
    }
}
