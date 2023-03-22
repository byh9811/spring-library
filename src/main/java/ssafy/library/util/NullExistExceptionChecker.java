package ssafy.library.util;

import org.springframework.stereotype.Component;
import ssafy.library.domain.*;
import ssafy.library.exception.NullExistException;

@Component
public class NullExistExceptionChecker {

    public void check(Member member) {
        if(member.getName()==null || member.getBirth()==null ||
                member.getPhone()==null || member.getEmail()==null)
            throw new NullExistException("필드에 null이 존재합니다!");

        check(member.getAddress());
    }

    public void check(Address address) {
        if(address==null ||
                address.getZipcode()==null ||
                address.getMain_address()==null ||
                address.getSub_address()==null)
            throw new NullExistException("필드에 null이 존재합니다!");
    }

    public void check(Category category) {
        if(category.getName()==null)
            throw new NullExistException("필드에 null이 존재합니다!");
    }

    public void check(BookInfo bookInfo) {
        if(bookInfo.getIsbn()==null ||
                bookInfo.getCategory()==null ||
                bookInfo.getAuthor()==null ||
                bookInfo.getName()==null ||
                bookInfo.getPublisher()==null ||
                bookInfo.getCreatedDate()==null)
            throw new NullExistException("필드에 null이 존재합니다!");
    }

    public void check(Book book) {
        if(book.getBookInfo()==null ||
                book.getStatus()==null)
            throw new NullExistException("필드에 null이 존재합니다!");
    }
}
