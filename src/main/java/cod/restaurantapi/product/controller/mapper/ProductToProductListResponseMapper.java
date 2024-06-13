package cod.restaurantapi.product.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.response.ProductListResponse;
import cod.restaurantapi.product.service.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductToProductListResponseMapper extends BaseMapper<Product, ProductListResponse> {
    ProductToProductListResponseMapper INSTANCE = Mappers.getMapper(ProductToProductListResponseMapper.class);
}
