package cod.restaurantapi.product.service.impl;

import cod.restaurantapi.product.model.enums.CategoryStatus;
import cod.restaurantapi.product.repository.CategoryRepository;
import cod.restaurantapi.product.repository.entity.CategoryEntity;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void givenCategoryId_whenCategoryNotExist_thenThrowException() {

        Long categoryId = 1L;


        Mockito.when(categoryRepository.findById(Mockito.any(Long.class)))
                .thenThrow(RuntimeException.class);


        assertThrows(RuntimeException.class,
                () -> categoryService.findById(categoryId));


    }

}