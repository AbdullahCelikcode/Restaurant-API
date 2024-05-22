package cod.restaurantapi.diningtable.controller;

import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.common.model.RMAPage;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableAddRequestToCommandMapper;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableListRequestToCommandMapper;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableToDiningTableResponseMapper;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableUpdateRequestToCommandMapper;
import cod.restaurantapi.diningtable.controller.request.DiningTableAddRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableListRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableUpdateRequest;
import cod.restaurantapi.diningtable.controller.response.DiningTableResponse;
import cod.restaurantapi.diningtable.service.DiningTableService;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import cod.restaurantapi.diningtable.service.domain.DiningTable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/dining-table")
class DiningTableController {
    private final DiningTableService diningTableService;

    private static final DiningTableAddRequestToCommandMapper diningTableAddRequestToCommandMapper = DiningTableAddRequestToCommandMapper.INSTANCE;
    private static final DiningTableToDiningTableResponseMapper diningTableToDiningTableResponseMapper = DiningTableToDiningTableResponseMapper.INSTANCE;
    private static final DiningTableUpdateRequestToCommandMapper diningTableUpdateRequestToCommandMapper = DiningTableUpdateRequestToCommandMapper.INSTANCE;
    private static final DiningTableListRequestToCommandMapper diningTableListRequestToCommandMapper = DiningTableListRequestToCommandMapper.INSTANCE;

    @GetMapping("/{id}")
    public BaseResponse<DiningTableResponse> getDiningTableById(@PathVariable @Positive Long id) {

        DiningTable diningTable = diningTableService.findById(id);
        DiningTableResponse diningTableResponse = diningTableToDiningTableResponseMapper.map(diningTable);

        return BaseResponse.successOf(diningTableResponse);
    }

    @PostMapping("/all")
    public BaseResponse<RMAPage<DiningTableResponse>> findAllDiningTables(
            @RequestBody @Valid DiningTableListRequest diningTableListRequest) {

        RMAPageResponse<DiningTable> diningTableList = diningTableService.findAll(diningTableListRequestToCommandMapper.map(diningTableListRequest));
        RMAPage<DiningTableResponse> diningTableResponse = RMAPage.<DiningTableResponse>builder()
                .map(diningTableToDiningTableResponseMapper.map(diningTableList.getContent()), diningTableList)
                .build();

        return BaseResponse.successOf(diningTableResponse);

    }


    @PostMapping
    public BaseResponse<Void> diningTableAdd(@RequestBody @Valid DiningTableAddRequest diningTableAddRequest) {

        diningTableService.save(diningTableAddRequestToCommandMapper.map(diningTableAddRequest));

        return BaseResponse.SUCCESS;
    }

    @PutMapping("/{id}")
    public BaseResponse<DiningTableResponse> diningTableUpdate(@PathVariable @Positive Long id,
                                                               @RequestBody @Valid DiningTableUpdateRequest diningTableUpdateRequest
    ) {
        DiningTableUpdateCommand diningTableUpdateCommand = diningTableUpdateRequestToCommandMapper.map(diningTableUpdateRequest);
        DiningTable diningTable = diningTableService.update(id, diningTableUpdateCommand);

        return BaseResponse.successOf(diningTableToDiningTableResponseMapper.map(diningTable));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> diningTableDelete(@PathVariable @Positive Long id) {
        diningTableService.deleteById(id);
        return BaseResponse.SUCCESS;
    }

}
