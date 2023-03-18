package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.library.domain.Book;
import ssafy.library.repository.BookRepository;
import ssafy.library.repository.dto.BookSearch;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public String add(Book book) {
        return bookRepository.save(book);
    }

}
