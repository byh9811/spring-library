package ssafy.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.library.domain.Loan;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LoanRepository {

    private final EntityManager em;

    public Long save(Loan loan) {
        em.persist(loan);
        return loan.getId();
    }
}
