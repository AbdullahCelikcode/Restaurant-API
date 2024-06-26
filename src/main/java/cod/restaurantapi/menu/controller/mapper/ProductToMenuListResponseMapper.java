package cod.restaurantapi.menu.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.menu.controller.response.MenuResponse;
import cod.restaurantapi.product.service.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductToMenuListResponseMapper extends BaseMapper<Product, MenuResponse> {
    ProductToMenuListResponseMapper INSTANCE = Mappers.getMapper(ProductToMenuListResponseMapper.class);

    @Mapping(source = "id", target = "product.id")
    @Mapping(source = "name", target = "product.name")
    @Mapping(source = "ingredient", target = "product.ingredient")
    @Mapping(source = "price", target = "product.price")
    @Mapping(source = "currency", target = "product.currency")
    @Mapping(source = "extent", target = "product.extent")
    @Mapping(source = "extentType", target = "product.extentType")
    @Mapping(source = "category", target = "category")
    MenuResponse map(Product source);
}
