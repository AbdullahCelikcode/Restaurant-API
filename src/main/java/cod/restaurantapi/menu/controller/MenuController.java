package cod.restaurantapi.menu.controller;

import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.common.model.RMAPage;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.menu.controller.mapper.MenuDtoToMenuListResponseMapper;
import cod.restaurantapi.menu.controller.mapper.MenuRequestToMenuListCommandMapper;
import cod.restaurantapi.menu.controller.request.MenuRequest;
import cod.restaurantapi.menu.controller.response.MenuResponse;
import cod.restaurantapi.menu.service.MenuDTO;
import cod.restaurantapi.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    private static final MenuRequestToMenuListCommandMapper menuRequestToMenuListCommandMapper = MenuRequestToMenuListCommandMapper.INSTANCE;
    private static final MenuDtoToMenuListResponseMapper menuDtoToMenuListResponseMapper = MenuDtoToMenuListResponseMapper.INSTANCE;

    @PostMapping("/api/v1/menu")
    public BaseResponse<RMAPage<MenuResponse>> getMenu(@RequestBody MenuRequest menuRequest) {
        RMAPageResponse<MenuDTO> menu = menuService.getMenu(menuRequestToMenuListCommandMapper.map(menuRequest));


        RMAPage<MenuResponse> pageResponse = RMAPage.<MenuResponse>builder()
                .map(menuDtoToMenuListResponseMapper.map(menu.getContent()), menu)
                .build();

        return BaseResponse.successOf(pageResponse);
    }

}
