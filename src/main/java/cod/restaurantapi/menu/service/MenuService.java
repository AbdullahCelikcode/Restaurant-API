package cod.restaurantapi.menu.service;

import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.menu.service.command.MenuListCommand;
import cod.restaurantapi.product.service.domain.Product;

public interface MenuService {

    RMAPageResponse<Product> getMenu(MenuListCommand menuListCommand);

}
