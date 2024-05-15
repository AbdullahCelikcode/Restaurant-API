package cod.restaurantapi.product.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.response.ProductResponse;
import cod.restaurantapi.product.service.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductToProductResponseMapper extends BaseMapper<Product, ProductResponse> {

    ProductToProductResponseMapper INSTANCE = Mappers.getMapper(ProductToProductResponseMapper.class);
}
