package ssafy.library.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssafy.library.domain.Category;
import ssafy.library.exception.DataOutOfRangeException;
import ssafy.library.exception.DuplicateException;
import ssafy.library.exception.NullExistException;
import ssafy.library.repository.CategoryRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void 카테고리등록_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");

        // when
        categoryService.add(category);

        // then
        categoryRepository.findById(1L);
    }

    @Test
    public void 카테고리등록_실패_널입력() throws Exception {

        // given
        Category category = new Category(null, null);

        // when & then
        assertThrows(NullExistException.class, () -> {
            categoryService.add(category);
        });

    }

    @Test
    public void 카테고리등록_실패_입력값() throws Exception {

        // given
        Category category = new Category(null, ".".repeat(256));

        // when & then
        assertThrows(DataOutOfRangeException.class, () -> {
            categoryService.add(category);
        });

    }

    @Test
    public void 카테고리등록_실패_이름중복() throws Exception {

        // given
        Category category1 = new Category(null, "문학");
        Category category2 = new Category(null, "문학");

        // when & then
        assertThrows(DuplicateException.class, () -> {
            categoryService.add(category1);
            categoryService.add(category2);
        });

    }

    @Test
    public void 카테고리전체조회_성공() throws Exception {

        // given
        Category category1 = new Category(null, "문학");
        Category category2 = new Category(null, "과학");
        Category category3 = new Category(null, "소설");

        // when
        categoryService.add(category1);
        categoryService.add(category2);
        categoryService.add(category3);
        List<Category> categories = categoryService.getAllCategories();

        // then
        assertThat(categories.size()).isEqualTo(3);

    }

    @Test
    public void 카테고리이름수정_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");

        // when
        categoryService.updateName(category, "과학");
        categoryRepository.save(category);

        // then
        Category updatedCategory = categoryRepository.findById(1L);
        assertThat(updatedCategory.getName()).isEqualTo("과학");

    }

    @Test
    public void 카테고리삭제_성공() throws Exception {

        // given
        Category category = new Category(null, "문학");

        // when
        Long saveId = categoryRepository.save(category);
        Long deleteId = categoryRepository.delete(category.getCategory_id());

        // then
        assertThat(saveId).isEqualTo(deleteId);

    }

}