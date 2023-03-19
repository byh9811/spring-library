package ssafy.library.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book {

    @Id
    private String book_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn")
    @NotNull
    private BookInfo bookInfo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BookStatus status;

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
