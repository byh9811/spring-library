package ssafy.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.Address;
import ssafy.library.domain.Member;
import ssafy.library.exception.*;
import ssafy.library.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);

        // when
        Long memberId = memberService.join(member);

        // then
        Member savedMember = memberRepository.findById(memberId);
        assertThat(member).isEqualTo(savedMember);

    }

    @Test
    public void 널데이터에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", null);
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811ver.com", address);

        // when & then
        assertThrows(NullExistException.class, () -> {
            memberService.join(member);
        });
    }

    @Test
    public void 데이터범위에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "19981128", "01071852569", "byh9811ver.com", address);

        // when & then
        assertThrows(DataOutOfRangeException.class, () -> {
            memberService.join(member);
        });
    }

    @Test
    public void 중복에러() throws Exception {

        // given
        Address address1 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member1 = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address1);

        Address address2 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member2 = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address2);

        // when & then
        assertThrows(DuplicateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });
    }

    @Test
    public void 미래에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "301128", "01071852569", "byh9811@naver.com", address);

        // when & then
        assertThrows(DateException.class, () -> {
            memberService.join(member);
        });

    }

    @Test
    public void 이메일포맷에러() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811ver.com", address);

        // when & then
        assertThrows(EmailFormatException.class, () -> {
            memberService.join(member);
        });

    }

    @Test
    public void 회원전체조회_성공() throws Exception {

        // given
        Address address1 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member1 = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address1);

        Address address2 = new Address("456", "광주광역시", "엘리시아 307호");
        Member member2 = new Member(null, "BAE", "981128", "11", "byh1128@naver.com", address2);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> members = memberService.findMembers();

        // then
        assertThat(members.size()).isEqualTo(2);

    }

    @Test
    public void 회원이름조회_성공() throws Exception {

        // given
        Address address1 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member1 = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address1);

        Address address2 = new Address("456", "광주광역시", "엘리시아 307호");
        Member member2 = new Member(null, "BAE", "981128", "11", "byh1128@naver.com", address2);

        Address address3 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member3 = new Member(null, "SONG", "123456", "22", "byh9811@daum.com", address3);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        List<Member> findMembers = memberService.findMembersByName("BAE");

        // then
        assertThat(findMembers.size()).isEqualTo(2);
    }

    @Test
    public void 회원이메일수정_성공() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);

        // when
        memberService.updateEmail(member, "byh1128@naver.com");
        Long saveId = memberRepository.save(member);

        // then
        Member savedMember = memberRepository.findById(saveId);
        assertThat(savedMember.getEmail()).isEqualTo("byh1128@naver.com");
    }
    
    @Test
    public void 회원연락처수정_성공() throws Exception {
        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);

        // when
        memberService.updatePhone(member, "01071850000");
        Long saveId = memberRepository.save(member);

        // then
        Member savedMember = memberRepository.findById(saveId);
        assertThat(savedMember.getPhone()).isEqualTo("01071850000");
    }
    
    @Test
    public void 회원주소수정_성공() throws Exception {
        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);

        // when
        Address newAddress = new Address("456", "서울특별시", "노원구");
        memberService.updateAddress(member, newAddress);
        Long saveId = memberRepository.save(member);

        // then
        Member savedMember = memberRepository.findById(saveId);
        assertThat(savedMember.getAddress()).isEqualTo(newAddress);
    }

    @Test
    public void 회원연락처수정_실패() throws Exception {

        // given
        Address address1 = new Address("123", "광주광역시", "엘리시아 306호");
        Member member1 = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address1);

        Address address2 = new Address("456", "광주광역시", "엘리시아 307호");
        Member member2 = new Member(null, "BAE", "981128", "11", "byh1128@naver.com", address2);

        // when & then
        assertThrows(DuplicateException.class, () -> {
            memberRepository.save(member1);
            memberRepository.save(member2);
            memberService.updatePhone(member1, "11");
        });

    }

    @Test
    public void 회원이메일수정_실패() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);

        // when & then
        assertThrows(EmailFormatException.class, () -> {
            memberService.updateEmail(member, "byh1128.com");
            memberRepository.save(member);
        });

    }

    @Test
    public void 회원탈퇴_성공() throws Exception {

        // given
        Address address = new Address("123", "광주광역시", "엘리시아 306호");
        Member member = new Member(null, "BAE", "981128", "01071852569", "byh9811@naver.com", address);

        // when
        Long saveId = memberService.join(member);
        Long removeId = memberService.quit(member.getId());

        // then
        assertThat(removeId).isEqualTo(saveId);
    }
}