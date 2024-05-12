package cod.restaurantapi.product.service.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import cod.restaurantapi.product.service.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductAddCommandToProductMapper extends BaseMapper<ProductAddCommand, Product> {

    ProductAddCommandToProductMapper INSTANCE = Mappers.getMapper(ProductAddCommandToProductMapper.class);
}
