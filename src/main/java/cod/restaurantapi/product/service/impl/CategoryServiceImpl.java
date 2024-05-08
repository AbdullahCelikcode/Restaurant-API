package cod.restaurantapi.product.service.impl;

import cod.restaurantapi.product.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.product.model.enums.CategoryStatus;
import cod.restaurantapi.product.repository.CategoryRepository;
import cod.restaurantapi.product.repository.entity.CategoryEntity;
import cod.restaurantapi.product.service.CategoryService;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.command.CategoryListCommand;
import cod.restaurantapi.product.service.command.CategoryUpdateCommand;
import cod.restaurantapi.product.service.domain.Category;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.product.service.mapper.CategoryCreateCommandToCategoryMapper;
import cod.restaurantapi.product.service.mapper.CategoryEntityToCategory;
import cod.restaurantapi.product.service.mapper.CategoryToCategoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static final CategoryEntityToCategory categoryEntityToCategoryMapper = CategoryEntityToCategory.INSTANCE;

    private static final CategoryCreateCommandToCategoryMapper categoryCreateCommandToCategoryMapper = CategoryCreateCommandToCategoryMapper.INSTANCE;

    private static final CategoryToCategoryEntityMapper categoryToCategoryEntityMapper = CategoryToCategoryEntityMapper.INSTANCE;


    @Override
    public RMAPageResponse findAll(CategoryListCommand categoryListCommand) {

        Page<CategoryEntity> responseList = categoryRepository.findAll(
                categoryListCommand.toSpecification(CategoryEntity.class),
                categoryListCommand.toPageable());

        return RMAPageResponse.<Category>builder()
                .page(responseList)
                .content(categoryEntityToCategoryMapper.map(responseList.getContent()))
                .sortedBy(categoryListCommand.getSorting())
                .filteredBy(categoryListCommand.getFilter())
                .build();


    }

    @Override
    public void save(CategoryCreateCommand categoryCreateCommand) {
        Category category = categoryCreateCommandToCategoryMapper.map(categoryCreateCommand);
        category.active();
        CategoryEntity categoryEntity = categoryToCategoryEntityMapper.map(category);
        categoryRepository.save(categoryEntity);

    }

    @Override
    public Category findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return categoryEntityToCategoryMapper.map(categoryEntity);
    }

    @Override
    public Category update(Long id, CategoryUpdateCommand categoryUpdateCommand) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryEntity.setName(categoryUpdateCommand.getName());
        categoryEntity.setStatus(categoryUpdateCommand.getStatus());

        categoryRepository.save(categoryEntity);


        return categoryEntityToCategoryMapper.map(categoryEntity);
    }


    @Override
    public void delete(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryEntity.setStatus(CategoryStatus.DELETED);

        categoryRepository.save(categoryEntity);
    }

}
