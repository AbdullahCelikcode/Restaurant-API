package cod.restaurantapi.category.service.mapper;

import cod.restaurantapi.category.repository.entity.CategoryEntity;
import cod.restaurantapi.category.service.command.CategoryUpdateCommand;
import cod.restaurantapi.common.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryUpdateCommandToCategoryEntityMapper extends BaseMapper<CategoryUpdateCommand, CategoryEntity> {

    void update(@MappingTarget CategoryUpdateCommand target, CategoryEntity source);


    CategoryUpdateCommandToCategoryEntityMapper INSTANCE = Mappers.getMapper(CategoryUpdateCommandToCategoryEntityMapper.class);

}
