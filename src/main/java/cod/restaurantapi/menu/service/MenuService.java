package cod.restaurantapi.menu.service;

import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.menu.service.command.MenuListCommand;
import cod.restaurantapi.menu.service.domain.Menu;

public interface MenuService {

    RMAPageResponse<Menu> getMenu(MenuListCommand menuListCommand);

}
