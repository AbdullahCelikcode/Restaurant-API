package cod.restaurantapi.product.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.repository.entity.CategoryEntity;
import cod.restaurantapi.product.service.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryToCategoryEntityMapper extends BaseMapper<Category, CategoryEntity> {
    CategoryToCategoryEntityMapper INSTANCE = Mappers.getMapper(CategoryToCategoryEntityMapper.class);

}
