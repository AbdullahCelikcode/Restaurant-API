package cod.restaurantapi.category.service.impl;

import cod.restaurantapi.category.controller.exceptions.CategoryAlreadyExistException;
import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.category.repository.CategoryRepository;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.category.service.CategoryService;
import cod.restaurantapi.category.service.command.CategoryCreateCommand;
import cod.restaurantapi.category.service.command.CategoryListCommand;
import cod.restaurantapi.category.service.command.CategoryUpdateCommand;
import cod.restaurantapi.category.service.domain.Category;
import cod.restaurantapi.category.service.mapper.CategoryCreateCommandToCategoryMapper;
import cod.restaurantapi.category.service.mapper.CategoryEntityToCategory;
import cod.restaurantapi.category.service.mapper.CategoryToCategoryEntityMapper;
import cod.restaurantapi.category.service.mapper.CategoryUpdateCommandToCategoryEntityMapper;
import cod.restaurantapi.common.model.RMAPageResponse;
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
    private static final CategoryUpdateCommandToCategoryEntityMapper categoryUpdateCommandToCategoryEntityMapper = CategoryUpdateCommandToCategoryEntityMapper.INSTANCE;


    @Override
    public RMAPageResponse<Category> findAll(CategoryListCommand categoryListCommand) {

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

        this.checkIfExistingOfCategory(categoryCreateCommand);

        Category category = categoryCreateCommandToCategoryMapper.map(categoryCreateCommand);
        category.active();
        CategoryEntity categoryEntity = categoryToCategoryEntityMapper.map(category);
        categoryRepository.save(categoryEntity);

    }

    private void checkIfExistingOfCategory(CategoryCreateCommand categoryCreateCommand) {
        if (categoryRepository.findByName(categoryCreateCommand.getName()).isPresent()) {
            throw new CategoryAlreadyExistException();
        }
    }


    @Override
    public Category findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return categoryEntityToCategoryMapper.map(categoryEntity);
    }


    @Override
    public Category update(Long id, CategoryUpdateCommand categoryUpdateCommand) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        this.checkExistingOfCategoryNameIfChanged(categoryUpdateCommand, categoryEntity);

        categoryUpdateCommandToCategoryEntityMapper.update(categoryEntity, categoryUpdateCommand);
        categoryRepository.save(categoryEntity);

        return categoryEntityToCategoryMapper.map(categoryEntity);
    }

    private void checkExistingOfCategoryNameIfChanged(CategoryUpdateCommand categoryUpdateCommand, CategoryEntity categoryEntity) {
        if (categoryRepository.findByName(categoryUpdateCommand.getName()).isPresent()
                && !categoryUpdateCommand.getName().equals(categoryEntity.getName())) {
            throw new CategoryAlreadyExistException();
        }
    }


    @Override
    public void delete(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryEntity.setStatus(CategoryStatus.DELETED);

        categoryRepository.save(categoryEntity);
    }

}
