package cod.restaurantapi.product.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.repository.entity.CategoryEntity;
import cod.restaurantapi.product.service.command.CategoryCreateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryCreateCommandToEntityMapper extends BaseMapper<CategoryCreateCommand, CategoryEntity> {
    CategoryCreateCommandToEntityMapper INSTANCE = Mappers.getMapper(CategoryCreateCommandToEntityMapper.class);
}
