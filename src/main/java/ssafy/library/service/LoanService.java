package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.library.domain.Loan;
import ssafy.library.repository.BookRepository;
import ssafy.library.repository.LoanRepository;
import ssafy.library.repository.MemberRepository;
import ssafy.library.util.*;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public Long checkout(Long memberId, String bookId) {
        Loan loan = Loan.createLoan(memberRepository.findById(memberId), bookRepository.findById(bookId));
        return loanRepository.save(loan);
    }

    public Long returnBook(Long loan_id) {
        return loanRepository.findById(loan_id).returnBook();
    }

}
