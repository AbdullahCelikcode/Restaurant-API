package cod.restaurantapi.product.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.product.controller.request.ProductUpdateRequest;
import cod.restaurantapi.product.service.command.ProductUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductUpdateRequestToCommandMapper extends BaseMapper<ProductUpdateRequest, ProductUpdateCommand> {

    ProductUpdateRequestToCommandMapper INSTANCE = Mappers.getMapper(ProductUpdateRequestToCommandMapper.class);
}
