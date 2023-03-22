package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.library.domain.Book;
import ssafy.library.repository.BookRepository;
import ssafy.library.repository.dto.BookSearch;
import ssafy.library.util.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final NullExistExceptionChecker nullExistExceptionChecker;
    private final DataOutOfRangeExceptionChecker dataOutOfRangeExceptionChecker;

    public String add(Book book) {
        validation(book);
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> find(BookSearch bookSearch) {
        return bookRepository.findByCriteria(bookSearch);
    }

    public String delete(String id) {
        return bookRepository.remove(id);
    }

    private void validation(Book book) {
        nullExistExceptionChecker.check(book);
        dataOutOfRangeExceptionChecker.check(book);
    }

}
