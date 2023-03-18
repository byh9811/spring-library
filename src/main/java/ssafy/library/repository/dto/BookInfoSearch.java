package ssafy.library.repository.dto;

import lombok.Getter;
import lombok.Setter;
import ssafy.library.domain.Category;

@Getter
@Setter
public class BookInfoSearch {

    private Category category;
    private String name;
    private String publisher;
    private String author;

}
