package cod.restaurantapi.menu.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.menu.controller.response.MenuResponse;
import cod.restaurantapi.menu.service.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuDtoToMenuListResponseMapper extends BaseMapper<MenuDTO, MenuResponse> {
    MenuDtoToMenuListResponseMapper INSTANCE = Mappers.getMapper(MenuDtoToMenuListResponseMapper.class);
}
