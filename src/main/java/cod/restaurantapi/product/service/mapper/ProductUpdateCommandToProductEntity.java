package cod.restaurantapi.product.service.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import cod.restaurantapi.product.service.command.ProductUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductUpdateCommandToProductEntity extends BaseMapper<ProductUpdateCommand, ProductEntity> {

    void update(@MappingTarget ProductUpdateCommand target, ProductEntity source);

    ProductUpdateCommandToProductEntity INSTANCE = Mappers.getMapper(ProductUpdateCommandToProductEntity.class);


}
