package ssafy.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.library.domain.Member;
import ssafy.library.domain.Reservation;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public Long save(Reservation reservation) {
        em.persist(reservation);
        return reservation.getId();
    }

    public Long remove(Long id) {
        Reservation reservation = em.find(Reservation.class, id);
        em.remove(reservation);
        return id;
    }

    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class)
                .getResultList();
    }

    public List<Reservation> findByMemberId(Member member) {
        return em.createQuery("select r from Reservation r where r.member=:member", Reservation.class)
                .setParameter("member", member)
                .getResultList();
    }

}
