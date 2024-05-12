package cod.restaurantapi.category.service.mapper;


import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.category.service.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryEntityToCategory extends BaseMapper<CategoryEntity, Category> {
    CategoryEntityToCategory INSTANCE = Mappers.getMapper(CategoryEntityToCategory.class);
}
