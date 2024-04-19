package cod.restaurantapi.product.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.request.CategoryUpdateRequest;
import cod.restaurantapi.product.service.command.CategoryUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryUpdateRequestToCommandMapper extends BaseMapper<CategoryUpdateRequest, CategoryUpdateCommand> {

    CategoryUpdateRequestToCommandMapper INSTANCE = Mappers.getMapper(CategoryUpdateRequestToCommandMapper.class);
}
