package ssafy.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.library.domain.BookInfo;
import ssafy.library.domain.Category;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookInfoRepository {

    private final EntityManager em;

    public String save(BookInfo bookInfo) {
        em.persist(bookInfo);
        return bookInfo.getIsbn();
    }

    public List<BookInfo> findByName(String name) {
        return em.createQuery("select b from BookInfo b where b.name=:name", BookInfo.class)
                .setParameter("name", name)
                .getResultList();
    }

    public BookInfo findById(String isbn) {
        return em.find(BookInfo.class, isbn);
    }
}
