package cod.restaurantapi.product.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.request.ProductListRequest;
import cod.restaurantapi.product.service.command.ProductListCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductListRequestToProductListCommandMapper extends BaseMapper<ProductListRequest, ProductListCommand> {

    ProductListRequestToProductListCommandMapper INSTANCE = Mappers.getMapper(ProductListRequestToProductListCommandMapper.class);

}
