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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String birth;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    @Embedded
    @NotNull
    private Address address;

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }
}
