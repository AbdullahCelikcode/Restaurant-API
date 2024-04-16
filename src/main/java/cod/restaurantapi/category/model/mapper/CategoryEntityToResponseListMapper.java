package cod.restaurantapi.category.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.category.controller.response.CategoryResponse;
import cod.restaurantapi.category.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryEntityToResponseListMapper extends BaseMapper<CategoryEntity, CategoryResponse> {

    CategoryEntityToResponseListMapper INSTANCE = Mappers.getMapper(CategoryEntityToResponseListMapper.class);
}
