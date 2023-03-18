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
}
