package ssafy.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.Address;
import ssafy.library.domain.Category;
import ssafy.library.domain.Member;
import ssafy.library.repository.CategoryRepository;
import ssafy.library.repository.MemberRepository;
import ssafy.library.util.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final NullExistExceptionChecker nullExistExceptionChecker;
    private final DataOutOfRangeExceptionChecker dataOutOfRangeExceptionChecker;
    private final DuplicateExceptionChecker duplicateExceptionChecker;

    public Long add(Category category) {
        validate(category);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void updateName(Category category, String name) {
        validate(category);
        category.updateName(name);
    }

    private void validate(Category category) {
        nullExistExceptionChecker.check(category);
        dataOutOfRangeExceptionChecker.check(category);
        duplicateExceptionChecker.check(category);
    }

}
