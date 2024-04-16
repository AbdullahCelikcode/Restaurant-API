package cod.restaurantapi.category.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.category.controller.request.CategoryUpdateRequest;
import cod.restaurantapi.category.service.command.CategoryUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryUpdateRequestToCommandMapper extends BaseMapper<CategoryUpdateRequest, CategoryUpdateCommand> {

    CategoryUpdateRequestToCommandMapper INSTANCE = Mappers.getMapper(CategoryUpdateRequestToCommandMapper.class);
}
