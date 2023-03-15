package ssafy.library.dto;

import lombok.Getter;
import ssafy.library.domain.Address;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    @Size(max = 50)
    private String name;

    @NotEmpty(message = "생년월일은 필수 입니다.")
    @Size(max = 6)
    private String birth;

    @NotEmpty(message = "전화번호는 필수 입니다.")
    @Size(max = 11)
    private String phone;

    @NotEmpty(message = "이메일은 필수 입니다.")
    @Size(max = 100)
    @Email
    private String email;

    @NotEmpty(message = "우편번호는 필수 입니다.")
    @Size(max = 5)
    private String zipcode;

    @NotEmpty(message = "메인주소는 필수 입니다.")
    @Size(max = 255)
    private String main_address;

    @NotEmpty(message = "상세주소는 필수 입니다.")
    @Size(max = 255)
    private String sub_address;

}
