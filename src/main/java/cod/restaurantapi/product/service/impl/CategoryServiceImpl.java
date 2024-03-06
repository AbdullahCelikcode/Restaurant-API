package cod.restaurantapi.product.service.impl;

import cod.restaurantapi.product.model.enums.CategoryStatus;
import cod.restaurantapi.product.model.mapper.CategoryCreateCommandToEntityMapper;
import cod.restaurantapi.product.model.mapper.CategoryEntityToCategory;
import cod.restaurantapi.product.repository.CategoryRepository;
import cod.restaurantapi.product.repository.entity.CategoryEntity;
import cod.restaurantapi.product.service.CategoryService;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final CategoryCreateCommandToEntityMapper toEntity = CategoryCreateCommandToEntityMapper.INSTANCE;
    private static final CategoryEntityToCategory toCategory = CategoryEntityToCategory.INSTANCE;

    @Override
    public void save(CategoryCreateCommand categoryCreateCommand) {
        CategoryEntity categoryEntity = toEntity.map(categoryCreateCommand);
        categoryEntity.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(categoryEntity);

    }

    @Override
    public Category findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Is Not Exists"));
        return toCategory.map(categoryEntity);
    }
}
