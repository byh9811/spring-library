package ssafy.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ssafy.library.domain.BookInfo;
import ssafy.library.domain.Category;
import ssafy.library.repository.dto.BookInfoSearch;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookInfoRepository {

    private final EntityManager em;

    public String save(BookInfo bookInfo) {
        em.persist(bookInfo);
        return bookInfo.getIsbn();
    }

    public String remove(String isbn) {
        BookInfo info = em.find(BookInfo.class, isbn);
        em.remove(info);
        return info.getIsbn();
    }

    public List<BookInfo> findByName(String name) {
        return em.createQuery("select b from BookInfo b where b.name=:name", BookInfo.class)
                .setParameter("name", name)
                .getResultList();
    }

    public BookInfo findById(String isbn) {
        return em.find(BookInfo.class, isbn);
    }

    public List<BookInfo> findAll() {
        return em.createQuery("select b from BookInfo b", BookInfo.class).getResultList();
    }

    public List<BookInfo> findByCriteria(BookInfoSearch bookInfoSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookInfo> cq = cb.createQuery(BookInfo.class);
        Root<BookInfo> o = cq.from(BookInfo.class);
        List<Predicate> criteria = new ArrayList<>();

        // 카테고리 검색
        if (bookInfoSearch.getCategory() != null) {
            Predicate category = cb.equal(o.get("category"),
                    bookInfoSearch.getCategory());
            criteria.add(category);
        }

        // 도서명 검색
        if (StringUtils.hasText(bookInfoSearch.getName())) {
            Predicate name =
                    cb.like(o.get("name"), "%" +
                            bookInfoSearch.getName() + "%");
            criteria.add(name);
        }

        // 출판사 검색
        if (bookInfoSearch.getPublisher() != null) {
            Predicate publisher = cb.equal(o.get("publisher"),
                    bookInfoSearch.getPublisher());
            criteria.add(publisher);
        }

        // 저자 검색
        if (bookInfoSearch.getAuthor() != null) {
            Predicate author = cb.equal(o.get("author"),
                    bookInfoSearch.getAuthor());
            criteria.add(author);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[0])));
        TypedQuery<BookInfo> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();
    }

}
