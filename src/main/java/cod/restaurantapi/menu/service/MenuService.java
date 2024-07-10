package cod.restaurantapi.menu.service;

import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.menu.service.command.MenuListCommand;

public interface MenuService {

    RMAPageResponse<MenuDTO> getMenu(MenuListCommand menuListCommand);

}
