package cod.restaurantapi.category.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.category.controller.request.CategoryListRequest;
import cod.restaurantapi.category.service.command.CategoryListCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryListRequestToCommandListMapper extends BaseMapper<CategoryListRequest, CategoryListCommand> {
    CategoryListRequestToCommandListMapper INSTANCE = Mappers.getMapper(CategoryListRequestToCommandListMapper.class);
}
