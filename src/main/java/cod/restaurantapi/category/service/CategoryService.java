package cod.restaurantapi.category.service;

import cod.restaurantapi.category.service.command.CategoryCreateCommand;
import cod.restaurantapi.category.service.command.CategoryListCommand;
import cod.restaurantapi.category.service.command.CategoryUpdateCommand;
import cod.restaurantapi.category.service.domain.Category;
import cod.restaurantapi.category.service.domain.CategoryList;

public interface CategoryService {

    CategoryList findAll(CategoryListCommand categoryListCommand);

    void save(CategoryCreateCommand categoryCreateCommand);

    Category findById(Long id);

    Category update(Long id, CategoryUpdateCommand categoryUpdateCommand);

    void delete(Long id);
}
