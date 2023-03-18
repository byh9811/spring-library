package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssafy.library.domain.BookInfo;
import ssafy.library.domain.Member;
import ssafy.library.repository.BookInfoRepository;
import ssafy.library.repository.dto.BookInfoSearch;
import ssafy.library.util.DataOutOfRangeExceptionChecker;
import ssafy.library.util.DuplicateExceptionChecker;
import ssafy.library.util.NullExistExceptionChecker;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookInfoService {

    private final BookInfoRepository bookInfoRepository;
    private final NullExistExceptionChecker nullExistExceptionChecker;
    private final DataOutOfRangeExceptionChecker dataOutOfRangeExceptionChecker;
    private final DuplicateExceptionChecker duplicateExceptionChecker;

    public String add(BookInfo bookInfo) {
        validate(bookInfo);

        return bookInfoRepository.save(bookInfo);
    }

    public List<BookInfo> findAll() {
        return bookInfoRepository.findAll();
    }

    public List<BookInfo> find(BookInfoSearch bookInfoSearch) {
        return bookInfoRepository.findByCriteria(bookInfoSearch);
    }

    private void validate(BookInfo bookInfo) {
        nullExistExceptionChecker.check(bookInfo);
        dataOutOfRangeExceptionChecker.check(bookInfo);
        duplicateExceptionChecker.check(bookInfo);
    }

}
