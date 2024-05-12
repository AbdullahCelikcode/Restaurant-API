package cod.restaurantapi.product.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.request.ProductAddRequest;
import cod.restaurantapi.product.service.command.ProductAddCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductAddRequestToCommandMapper extends BaseMapper<ProductAddRequest, ProductAddCommand> {

    ProductAddRequestToCommandMapper INSTANCE = Mappers.getMapper(ProductAddRequestToCommandMapper.class);

}
