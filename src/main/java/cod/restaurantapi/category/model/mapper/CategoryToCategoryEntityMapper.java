package cod.restaurantapi.category.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.category.service.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryToCategoryEntityMapper extends BaseMapper<Category, CategoryEntity> {
    CategoryToCategoryEntityMapper INSTANCE = Mappers.getMapper(CategoryToCategoryEntityMapper.class);

}
