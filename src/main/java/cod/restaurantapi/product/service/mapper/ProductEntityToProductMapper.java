package cod.restaurantapi.product.service.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import cod.restaurantapi.product.service.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductEntityToProductMapper extends BaseMapper<ProductEntity, Product> {

    ProductEntityToProductMapper INSTANCE = Mappers.getMapper(ProductEntityToProductMapper.class);
}
