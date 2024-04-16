package cod.restaurantapi.category.service.impl;

import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.category.model.mapper.CategoryCreateCommandToCategoryMapper;
import cod.restaurantapi.category.model.mapper.CategoryEntityToCategory;
import cod.restaurantapi.category.model.mapper.CategoryToCategoryEntityMapper;
import cod.restaurantapi.category.repository.CategoryRepository;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.category.service.CategoryService;
import cod.restaurantapi.category.service.command.CategoryCreateCommand;
import cod.restaurantapi.category.service.command.CategoryListCommand;
import cod.restaurantapi.category.service.command.CategoryUpdateCommand;
import cod.restaurantapi.category.service.domain.Category;
import cod.restaurantapi.category.service.domain.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static final CategoryEntityToCategory categoryEntityToCategoryMapper = CategoryEntityToCategory.INSTANCE;

    private static final CategoryCreateCommandToCategoryMapper categoryCreateCommandToCategoryMapper = CategoryCreateCommandToCategoryMapper.INSTANCE;

    private static final CategoryToCategoryEntityMapper categoryToCategoryEntityMapper = CategoryToCategoryEntityMapper.INSTANCE;


    @Override
    public CategoryList findAll(CategoryListCommand categoryListCommand) {

        Page<CategoryEntity> responseList;

        if (categoryListCommand.getFilter() == null || categoryListCommand.getFilter().getName() == null) {
            responseList = categoryRepository
                    .findAll(PageRequest.of(categoryListCommand.getPagination().getPageNumber(),
                            categoryListCommand.getPagination().getPageSize()));

        } else {
            responseList = categoryRepository
                    .findAllByNameContainingIgnoreCase(categoryListCommand.getFilter().getName(),
                            PageRequest.of(categoryListCommand.getPagination().getPageNumber(),
                                    categoryListCommand.getPagination().getPageSize()));
        }

        return CategoryList.builder()
                .categoryList(categoryEntityToCategoryMapper.map(responseList.getContent()))
                .pageSize(responseList.getSize())
                .pageNumber(responseList.getNumber())
                .totalPageCount(responseList.getTotalPages())
                .totalElementCount(responseList.getTotalElements())
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
        categoryEntity.setStatus(CategoryStatus.INACTIVE);

        categoryRepository.save(categoryEntity);
    }

    public Page<CategoryEntity> findAll() {

        return categoryRepository.findAll(PageRequest.of(3, 5));
    }


}
