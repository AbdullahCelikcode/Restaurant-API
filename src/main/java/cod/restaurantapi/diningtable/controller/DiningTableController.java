package cod.restaurantapi.diningtable.controller;

import cod.restaurantapi.common.BaseResponse;
import cod.restaurantapi.common.model.RMAPage;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableAddRequestToCommandMapper;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableListRequestToCommandMapper;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableMergeRequestToDiningTableMergeCommand;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableStatusRequestToDiningTableStatusCommandMapper;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableToDiningTableResponseMapper;
import cod.restaurantapi.diningtable.controller.mapper.DiningTableUpdateRequestToCommandMapper;
import cod.restaurantapi.diningtable.controller.request.DiningTableAddRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableListRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableMergeRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableUpdateRequest;
import cod.restaurantapi.diningtable.controller.response.DiningTableResponse;
import cod.restaurantapi.diningtable.controller.response.DiningTableStatusRequest;
import cod.restaurantapi.diningtable.service.DiningTableService;
import cod.restaurantapi.diningtable.service.command.DiningTableMergeCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableStatusCommand;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
class DiningTableController {
    private final DiningTableService diningTableService;

    private static final DiningTableAddRequestToCommandMapper diningTableAddRequestToCommandMapper = DiningTableAddRequestToCommandMapper.INSTANCE;
    private static final DiningTableToDiningTableResponseMapper diningTableToDiningTableResponseMapper = DiningTableToDiningTableResponseMapper.INSTANCE;
    private static final DiningTableUpdateRequestToCommandMapper diningTableUpdateRequestToCommandMapper = DiningTableUpdateRequestToCommandMapper.INSTANCE;
    private static final DiningTableListRequestToCommandMapper diningTableListRequestToCommandMapper = DiningTableListRequestToCommandMapper.INSTANCE;
    private static final DiningTableStatusRequestToDiningTableStatusCommandMapper diningTableStatusRequestToCommand = DiningTableStatusRequestToDiningTableStatusCommandMapper.INSTANCE;
    private static final DiningTableMergeRequestToDiningTableMergeCommand diningTableMergeRequestToCommand = DiningTableMergeRequestToDiningTableMergeCommand.INSTANCE;

    @GetMapping("/api/v1/dining-table/{id}")
    public BaseResponse<DiningTableResponse> getDiningTableById(@PathVariable @Positive Long id) {

        DiningTable diningTable = diningTableService.findById(id);
        DiningTableResponse diningTableResponse = diningTableToDiningTableResponseMapper.map(diningTable);

        return BaseResponse.successOf(diningTableResponse);
    }

    @PostMapping("/api/v1/dining-tables")
    public BaseResponse<RMAPage<DiningTableResponse>> findAllDiningTables(
            @RequestBody @Valid DiningTableListRequest diningTableListRequest) {

        RMAPageResponse<DiningTable> diningTableList = diningTableService.findAll(
                diningTableListRequestToCommandMapper.map(diningTableListRequest));

        RMAPage<DiningTableResponse> diningTableResponse = RMAPage.<DiningTableResponse>builder()
                .map(diningTableToDiningTableResponseMapper.map(diningTableList.getContent()), diningTableList)
                .build();

        return BaseResponse.successOf(diningTableResponse);

    }

    @PostMapping("/api/v1/dining-table")
    public BaseResponse<Void> diningTableAdd(@RequestBody @Valid DiningTableAddRequest diningTableAddRequest) {

        diningTableService.save(diningTableAddRequestToCommandMapper.map(diningTableAddRequest));

        return BaseResponse.SUCCESS;
    }

    @PostMapping("/api/v1/dining-table/merge")
    public BaseResponse<Void> mergeDiningTables(
            @RequestBody @Valid DiningTableMergeRequest diningTableMergeRequest) {

        DiningTableMergeCommand diningTableMergeCommand = diningTableMergeRequestToCommand.map(diningTableMergeRequest);
        diningTableService.mergeDiningTables(diningTableMergeCommand);

        return BaseResponse.SUCCESS;
    }

    @PostMapping("/api/v1/dining-table/{id}/split")
    public BaseResponse<Void> mergeDiningTables(@PathVariable UUID id) {
        diningTableService.splitDiningTables(id);

        return BaseResponse.SUCCESS;
    }


    @PutMapping("/api/v1/dining-table/{id}/status")
    public BaseResponse<Void> changeDiningTableStatus(
            @RequestBody @Valid DiningTableStatusRequest diningTableStatusRequest, @PathVariable Long id) {
        DiningTableStatusCommand diningTableStatusCommand = diningTableStatusRequestToCommand.map(diningTableStatusRequest);
        diningTableService.changeStatus(diningTableStatusCommand, id);

        return BaseResponse.SUCCESS;

    }

    @PutMapping("/api/v1/dining-table/{id}")
    public BaseResponse<Void> diningTableUpdate(@PathVariable @Positive Long id,
                                                @RequestBody @Valid DiningTableUpdateRequest diningTableUpdateRequest
    ) {
        DiningTableUpdateCommand diningTableUpdateCommand = diningTableUpdateRequestToCommandMapper.map(diningTableUpdateRequest);
        diningTableService.update(id, diningTableUpdateCommand);

        return BaseResponse.SUCCESS;
    }

    @DeleteMapping("/api/v1/dining-table/{id}")
    public BaseResponse<Void> diningTableDelete(@PathVariable @Positive Long id) {
        diningTableService.deleteById(id);
        return BaseResponse.SUCCESS;
    }

}
