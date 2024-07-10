package cod.restaurantapi.menu.service.impl;

import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.menu.service.MenuDTO;
import cod.restaurantapi.menu.service.MenuService;
import cod.restaurantapi.menu.service.command.MenuListCommand;
import cod.restaurantapi.menu.service.mapper.ProductToMenuMapper;
import cod.restaurantapi.product.repository.ProductRepository;
import cod.restaurantapi.product.repository.entity.ProductEntity;
import cod.restaurantapi.product.service.domain.Product;
import cod.restaurantapi.product.service.mapper.ProductEntityToProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class MenuServiceImpl implements MenuService {

    private final ProductRepository productRepository;
    private final String currency;

    private static final ProductEntityToProductMapper productEntityToProductMapper = ProductEntityToProductMapper.INSTANCE;
    private static final ProductToMenuMapper productToMenuMapper = ProductToMenuMapper.INSTANCE;

    @Override
    public RMAPageResponse<MenuDTO> getMenu(MenuListCommand menuListCommand) {

        Page<ProductEntity> responseList = productRepository.findAll(
                menuListCommand.toSpecification(ProductEntity.class),
                menuListCommand.toPageable());

        List<Product> productList = productEntityToProductMapper.map(responseList.getContent());
        productList.forEach(product -> product.setCurrency(currency));

        List<MenuDTO> menuResponseList = productToMenuMapper.map(productList);

        return RMAPageResponse.<MenuDTO>builder()
                .page(responseList)
                .content(menuResponseList)
                .sortedBy(Sorting.of(responseList.getSort()))
                .filteredBy(menuListCommand.getFilter()).build();
    }
}
