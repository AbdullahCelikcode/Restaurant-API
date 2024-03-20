package cod.restaurantapi.product.service;

import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.command.CategoryUpdateCommand;
import cod.restaurantapi.product.service.domain.Category;

public interface CategoryService {
    void save(CategoryCreateCommand categoryCreateCommand);

    Category findById(Long id);

    Category update(Long id, CategoryUpdateCommand categoryUpdateCommand);

    void delete(Long id);
}
