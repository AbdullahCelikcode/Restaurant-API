package cod.restaurantapi.service;

import cod.restaurantapi.product.model.enums.CategoryStatus;
import cod.restaurantapi.product.repository.CategoryRepository;
import cod.restaurantapi.product.repository.entity.CategoryEntity;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.domain.Category;
import cod.restaurantapi.product.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    void CategoryServiceCreateTest() {

        CategoryCreateCommand categoryCreateCommand = CategoryCreateCommand.builder()
                .name("CategoryTest")
                .build();

        CategoryEntity categoryEntity = new CategoryEntity();

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        categoryService.save(categoryCreateCommand);

        verify(categoryRepository, times(1)).save(any(CategoryEntity.class));


    }

    @Test
    void CategoryServiceFindByIdTest() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("TestCategory")
                .status(CategoryStatus.ACTIVE)
                .build();

        when(categoryRepository.findById(categoryEntity.getId())).thenReturn(Optional.of(categoryEntity));

        Category category = categoryService.findById(1L);

        verify(categoryRepository, times(1)).findById(any(Long.class));
        Assertions.assertEquals("TestCategory", category.getName());
        Assertions.assertEquals(1L, category.getId());
        Assertions.assertEquals(CategoryStatus.ACTIVE, category.getStatus());
    }

}
