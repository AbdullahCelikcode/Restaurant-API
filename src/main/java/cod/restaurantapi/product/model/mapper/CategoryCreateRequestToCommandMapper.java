package cod.restaurantapi.product.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.request.CategoryAddRequest;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryCreateRequestToCommandMapper extends BaseMapper<CategoryAddRequest, CategoryCreateCommand> {
    CategoryCreateRequestToCommandMapper INSTANCE = Mappers.getMapper(CategoryCreateRequestToCommandMapper.class);
}
