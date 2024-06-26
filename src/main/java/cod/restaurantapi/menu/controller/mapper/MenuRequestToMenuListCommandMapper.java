package cod.restaurantapi.menu.controller.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.menu.controller.request.MenuRequest;
import cod.restaurantapi.menu.service.command.MenuListCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuRequestToMenuListCommandMapper extends BaseMapper<MenuRequest, MenuListCommand> {

    MenuRequestToMenuListCommandMapper INSTANCE = Mappers.getMapper(MenuRequestToMenuListCommandMapper.class);
}
