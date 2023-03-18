package ssafy.library.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssafy.library.domain.Category;
import ssafy.library.domain.Member;
import ssafy.library.exception.DuplicateException;
import ssafy.library.repository.CategoryRepository;
import ssafy.library.repository.MemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DuplicateExceptionChecker {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public void check(Member member) {
        checkPhone(member.getPhone());
        checkEmail(member.getEmail());
    }

    public void checkPhone(String phone) {
        List<Member> memberList = memberRepository.findByPhone(phone);
        if(!memberList.isEmpty())
            throw new DuplicateException("핸드폰 번호가 중복됩니다!");
    }

    public void checkEmail(String email) {
        List<Member> memberList = memberRepository.findByEmail(email);
        if(!memberList.isEmpty())
            throw new DuplicateException("이메일이 중복됩니다!");
    }

    public void check(Category category) {
        List<Category> categoryList = categoryRepository.findByName(category.getName());
        if(!categoryList.isEmpty())
            throw new DuplicateException("도서분류명이 중복됩니다!");
    }

}
