package cod.restaurantapi.product.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.request.CategoryListRequest;
import cod.restaurantapi.product.service.command.CategoryListCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryListRequestToCommandListMapper extends BaseMapper<CategoryListRequest, CategoryListCommand> {
    CategoryListRequestToCommandListMapper INSTANCE = Mappers.getMapper(CategoryListRequestToCommandListMapper.class);
}
