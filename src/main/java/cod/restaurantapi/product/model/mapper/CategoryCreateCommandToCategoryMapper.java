package cod.restaurantapi.product.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import cod.restaurantapi.product.service.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryCreateCommandToCategoryMapper extends BaseMapper<CategoryCreateCommand, Category> {
    CategoryCreateCommandToCategoryMapper INSTANCE = Mappers.getMapper(CategoryCreateCommandToCategoryMapper.class);
}
