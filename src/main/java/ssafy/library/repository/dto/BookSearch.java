package ssafy.library.repository.dto;

import lombok.Getter;
import lombok.Setter;
import ssafy.library.domain.BookStatus;

@Getter
@Setter
public class BookSearch {

    private String isbn;
    private String book_id;
    private BookStatus status;

}
