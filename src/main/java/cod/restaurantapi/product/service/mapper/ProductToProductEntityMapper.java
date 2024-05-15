package cod.restaurantapi.product.service.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import cod.restaurantapi.product.service.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductToProductEntityMapper extends BaseMapper<Product, ProductEntity> {
    ProductToProductEntityMapper INSTANCE = Mappers.getMapper(ProductToProductEntityMapper.class);

}
