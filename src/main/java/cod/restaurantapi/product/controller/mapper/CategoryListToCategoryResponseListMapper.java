package cod.restaurantapi.product.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.response.CategoryListResponse;
import cod.restaurantapi.product.service.domain.CategoryList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryListToCategoryResponseListMapper extends BaseMapper<CategoryList, CategoryListResponse> {
    CategoryListToCategoryResponseListMapper INSTANCE = Mappers.getMapper(CategoryListToCategoryResponseListMapper.class);
}
