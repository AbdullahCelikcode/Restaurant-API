package cod.restaurantapi.product.service.impl;

import cod.restaurantapi.product.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.product.model.enums.CategoryStatus;
import cod.restaurantapi.product.repository.CategoryRepository;
import cod.restaurantapi.product.repository.entity.CategoryEntity;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.command.CategoryUpdateCommand;
import cod.restaurantapi.product.service.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    void givenCreateCategory_thenSaveCategory() {

        CategoryCreateCommand categoryCreateCommand = CategoryCreateCommand.builder()
                .name("CategoryTest")
                .build();

        CategoryEntity categoryEntity = new CategoryEntity();

        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(categoryEntity);

        categoryService.save(categoryCreateCommand);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(CategoryEntity.class));


    }

    @Test
    void givenUpdateCategoryAndCategoryId_whenCategoryExist_thenUpdateCategory() {

        Long categoryId = 1L;
        CategoryUpdateCommand categoryUpdateCommand = CategoryUpdateCommand.builder()
                .name("CategoryUpdateTest")
                .status(CategoryStatus.INACTIVE)
                .build();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("TestCategory")
                .status(CategoryStatus.ACTIVE)
                .build();
        categoryEntity.setUpdatedAt(LocalDateTime.now());


        Mockito.when(categoryRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(categoryEntity));

        Category category = categoryService.update(categoryId, categoryUpdateCommand);

        Assertions.assertEquals(categoryUpdateCommand.getName(), category.getName());
        Assertions.assertEquals(categoryUpdateCommand.getStatus(), category.getStatus());
        Assertions.assertEquals(categoryEntity.getUpdatedAt(), category.getUpdatedAt());

    }

    @Test
    void givenUpdateCategoryAndCategoryId_whenCategoryNotExist_thenThrowCategoryNotFoundException() {
        Long categoryId = 1L;
        CategoryUpdateCommand categoryUpdateCommand = CategoryUpdateCommand.builder()
                .name("CategoryUpdateTest")
                .status(CategoryStatus.INACTIVE)
                .build();


        Mockito.when(categoryRepository.findById(Mockito.any(Long.class)))
                .thenThrow(CategoryNotFoundException.class);


        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.update(categoryId, categoryUpdateCommand));

    }

    @Test
    void givenValidCategoryId_whenCategoryExist_thenReturnCategory() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("TestCategory")
                .status(CategoryStatus.ACTIVE)
                .build();

        Mockito.when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));

        Category category = categoryService.findById(1L);

        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        Assertions.assertEquals("TestCategory", category.getName());
        Assertions.assertEquals(1L, category.getId());
        Assertions.assertEquals(CategoryStatus.ACTIVE, category.getStatus());
    }


    @Test
    void givenCategoryId_whenCategoryNotExist_thenThrowCategoryNotFoundException() {

        Long categoryId = 1L;


        Mockito.when(categoryRepository.findById(Mockito.any(Long.class)))
                .thenThrow(CategoryNotFoundException.class);


        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.findById(categoryId));

    }

    @Test
    void givenDeleteCategoryId_whenCategoryExist_thenSoftDeleteCategory() {
        Long categoryId = 1L;
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(categoryId)
                .name("TestCategory")
                .status(CategoryStatus.ACTIVE)
                .build();

        Mockito.when(categoryRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.ofNullable(categoryEntity));

        categoryService.delete(categoryId);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(CategoryEntity.class));

    }


    @Test
    void givenDeleteCategoryId_whenCategoryNotExist_thenThrowCategoryNotFoundException() {

        Long categoryId = 1L;


        Mockito.when(categoryRepository.findById(Mockito.any(Long.class)))
                .thenThrow(CategoryNotFoundException.class);


        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.delete(categoryId));

    }


}