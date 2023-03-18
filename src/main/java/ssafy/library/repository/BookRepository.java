package ssafy.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.library.domain.Book;
import ssafy.library.domain.BookInfo;
import ssafy.library.repository.dto.BookSearch;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public String save(Book book) {
        em.persist(book);
        return book.getBook_id();
    }

    public String remove(String book_id) {
        em.remove(em.find(Book.class, book_id));
        return book_id;
    }

    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    public List<Book> findByCriteria(BookSearch bookSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> o = cq.from(Book.class);
        Join<Book, BookInfo> m = o.join("bookInfo", JoinType.INNER);
        List<Predicate> criteria = new ArrayList<>();

        // 카테고리 검색
        if (bookSearch.getIsbn() != null) {
            Predicate category = cb.equal(m.<String>get("isbn"),
                    bookSearch.getIsbn());
            criteria.add(category);
        }

        // 출판사 검색
        if (bookSearch.getBook_id() != null) {
            Predicate publisher = cb.equal(o.get("book_id"),
                    bookSearch.getBook_id());
            criteria.add(publisher);
        }

        // 저자 검색
        if (bookSearch.getStatus() != null) {
            Predicate author = cb.equal(o.get("status"),
                    bookSearch.getStatus());
            criteria.add(author);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        TypedQuery<Book> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();
    }
}
