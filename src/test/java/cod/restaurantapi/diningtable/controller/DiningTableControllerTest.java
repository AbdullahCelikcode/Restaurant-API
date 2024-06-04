package cod.restaurantapi.diningtable.controller;

import cod.restaurantapi.RMAControllerTest;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.diningtable.controller.request.DiningTableAddRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableListRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableUpdateRequest;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import cod.restaurantapi.diningtable.service.DiningTableService;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import cod.restaurantapi.diningtable.service.domain.DiningTable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = DiningTableController.class)
class DiningTableControllerTest extends RMAControllerTest {

    @MockBean
    protected DiningTableService diningTableService;

    private static final String BASE_URL = "/api/v1/dining-table";


    @Test
    void givenGetDiningTablesById_whenValidInput_ThenReturnSuccess() throws Exception {

        //given

        Long diningTableId = 1L;

        //when

        DiningTable diningTable = DiningTable.builder()
                .id(diningTableId)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build();
        Mockito.when(diningTableService.findById(Mockito.anyLong())).thenReturn(diningTable);

        //then

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", diningTableId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(diningTable.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.size").value(diningTable.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(diningTable.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenGetDiningTablesById_whenNegativeInput_ThenReturnBadRequest() throws Exception {

        //given

        Long diningTableId = -1L;


        //then

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", diningTableId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //verify

        Mockito.verify(diningTableService, Mockito.never()).findById(Mockito.anyLong());
    }


    @Test
    void givenDiningTableListRequest_whenValidInput_ThenReturnDiningTableListResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();
        DiningTableListRequest.DiningTableFilter diningTableFilter = DiningTableListRequest.DiningTableFilter.builder()
                .size(4)
                .status(Collections.singleton(DiningTableStatus.AVAILABLE))
                .build();
        Sorting sorting = Sorting.builder()
                .property("size")
                .direction(Sort.Direction.ASC)
                .build();
        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .filter(diningTableFilter)
                .sorting(sorting)
                .build();
        //when

        List<DiningTable> diningTableList = new ArrayList<>();
        diningTableList.add(DiningTable.builder()
                .id(1L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(2L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(3L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        RMAPageResponse<DiningTable> diningTableRMAPageResponse = RMAPageResponse.<DiningTable>builder()
                .content(diningTableList)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(diningTableList.size())
                .totalElementCount((long) diningTableList.size())
                .sortedBy(sorting)
                .filteredBy(diningTableFilter)
                .build();

        Mockito.when(diningTableService.findAll(Mockito.any(DiningTableListCommand.class)))
                .thenReturn(diningTableRMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableRMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableRMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(diningTableRMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.size")
                        .value(diningTableFilter.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenDiningTableListRequest_whenWithoutFilter_ThenReturnDiningTableListResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();

        Sorting sorting = Sorting.builder()
                .property("size")
                .direction(Sort.Direction.ASC)
                .build();
        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .sorting(sorting)
                .build();
        //when

        List<DiningTable> diningTableList = new ArrayList<>();
        diningTableList.add(DiningTable.builder()
                .id(1L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(2L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(3L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        RMAPageResponse<DiningTable> diningTableRMAPageResponse = RMAPageResponse.<DiningTable>builder()
                .content(diningTableList)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(diningTableList.size())
                .totalElementCount((long) diningTableList.size())
                .sortedBy(sorting)
                .build();

        Mockito.when(diningTableService.findAll(Mockito.any(DiningTableListCommand.class)))
                .thenReturn(diningTableRMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableRMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableRMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(diningTableRMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenDiningTableListRequest_whenWithOutSorting_ThenReturnDiningTableListResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();
        DiningTableListRequest.DiningTableFilter diningTableFilter = DiningTableListRequest.DiningTableFilter.builder()
                .size(4)
                .status(Collections.singleton(DiningTableStatus.AVAILABLE))
                .build();
        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .filter(diningTableFilter)
                .build();
        //when

        List<DiningTable> diningTableList = new ArrayList<>();
        diningTableList.add(DiningTable.builder()
                .id(1L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(2L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(3L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        RMAPageResponse<DiningTable> diningTableRMAPageResponse = RMAPageResponse.<DiningTable>builder()
                .content(diningTableList)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(diningTableList.size())
                .totalElementCount((long) diningTableList.size())
                .filteredBy(diningTableFilter)
                .build();

        Mockito.when(diningTableService.findAll(Mockito.any(DiningTableListCommand.class)))
                .thenReturn(diningTableRMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableRMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableRMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(diningTableRMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.size")
                        .value(diningTableFilter.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenDiningTableListRequest_whenWithOutFilterAndSorting_ThenReturnDiningTableListResponse() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(3)
                .build();
        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .build();
        //when

        List<DiningTable> diningTableList = new ArrayList<>();
        diningTableList.add(DiningTable.builder()
                .id(1L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(2L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        diningTableList.add(DiningTable.builder()
                .id(3L)
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build());
        RMAPageResponse<DiningTable> diningTableRMAPageResponse = RMAPageResponse.<DiningTable>builder()
                .content(diningTableList)
                .pageNumber(pagination.getPageNumber())
                .pageSize(pagination.getPageSize())
                .totalPageCount(diningTableList.size())
                .totalElementCount((long) diningTableList.size())
                .build();

        Mockito.when(diningTableService.findAll(Mockito.any(DiningTableListCommand.class)))
                .thenReturn(diningTableRMAPageResponse);

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableRMAPageResponse.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableRMAPageResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalPageCount")
                        .value(diningTableRMAPageResponse.getTotalPageCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void givenDiningTableListRequest_whenWithOutPagination_ThenReturnBadRequest() throws Exception {

        //given

        DiningTableListRequest.DiningTableFilter diningTableFilter = DiningTableListRequest.DiningTableFilter.builder()
                .size(4)
                .status(Collections.singleton(DiningTableStatus.AVAILABLE))
                .build();
        Sorting sorting = Sorting.builder()
                .property("size")
                .direction(Sort.Direction.ASC)
                .build();
        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .filter(diningTableFilter)
                .sorting(sorting)
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void givenDiningTableListRequest_whenInvalidPageSize_ThenReturnBadRequest() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(1)
                .pageSize(-1)
                .build();
        DiningTableListRequest.DiningTableFilter diningTableFilter = DiningTableListRequest.DiningTableFilter.builder()
                .size(4)
                .status(Collections.singleton(DiningTableStatus.AVAILABLE))
                .build();
        Sorting sorting = Sorting.builder()
                .property("size")
                .direction(Sort.Direction.ASC)
                .build();
        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .filter(diningTableFilter)
                .sorting(sorting)
                .pagination(pagination)
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void givenDiningTableListRequest_whenInvalidPageNumber_ThenReturnBadRequest() throws Exception {

        //given
        Pagination pagination = Pagination.builder()
                .pageNumber(-1)
                .pageSize(1)
                .build();
        DiningTableListRequest.DiningTableFilter diningTableFilter = DiningTableListRequest.DiningTableFilter.builder()
                .size(4)
                .status(Collections.singleton(DiningTableStatus.AVAILABLE))
                .build();
        Sorting sorting = Sorting.builder()
                .property("size")
                .direction(Sort.Direction.ASC)
                .build();
        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .filter(diningTableFilter)
                .sorting(sorting)
                .pagination(pagination)
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void givenDiningTableAddRequest_whenValidInput_thenReturnSuccess() throws Exception {

        //given
        List<DiningTableAddRequest.DiningTables> givenDiningTableList = new ArrayList<>();
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(2)
                .count(5)
                .build());
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(4)
                .count(5)
                .build());


        DiningTableAddRequest diningTableAddRequest = DiningTableAddRequest.builder()
                .diningTablesList(givenDiningTableList)
                .build();

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //verify
        Mockito.verify(diningTableService, Mockito.times(1))
                .save(Mockito.any(DiningTableAddCommand.class));

    }

    @Test
    void givenDiningTableAddRequest_whenDuplicatedSize_thenReturnBadRequest() throws Exception {

        //given

        List<DiningTableAddRequest.DiningTables> givenDiningTableList = new ArrayList<>();
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(2)
                .count(5)
                .build());
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(2)
                .count(5)
                .build());


        DiningTableAddRequest diningTableAddRequest = DiningTableAddRequest.builder()
                .diningTablesList(givenDiningTableList)
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //verify

        Mockito.verify(diningTableService, Mockito.times(0))
                .save(Mockito.any(DiningTableAddCommand.class));

    }

    @Test
    void givenDiningTableAddRequest_whenNegativeSize_thenReturnBadRequest() throws Exception {

        //given

        List<DiningTableAddRequest.DiningTables> givenDiningTableList = new ArrayList<>();
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(-2)
                .count(5)
                .build());
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(2)
                .count(5)
                .build());


        DiningTableAddRequest diningTableAddRequest = DiningTableAddRequest.builder()
                .diningTablesList(givenDiningTableList)
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //verify

        Mockito.verify(diningTableService, Mockito.times(0))
                .save(Mockito.any(DiningTableAddCommand.class));

    }

    @Test
    void givenDiningTableAddRequest_whenNegativeCount_thenReturnBadRequest() throws Exception {

        //given

        List<DiningTableAddRequest.DiningTables> givenDiningTableList = new ArrayList<>();
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(2)
                .count(5)
                .build());
        givenDiningTableList.add(DiningTableAddRequest.DiningTables.builder()
                .size(2)
                .count(-5)
                .build());


        DiningTableAddRequest diningTableAddRequest = DiningTableAddRequest.builder()
                .diningTablesList(givenDiningTableList)
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //verify

        Mockito.verify(diningTableService, Mockito.times(0))
                .save(Mockito.any(DiningTableAddCommand.class));

    }

    @Test
    void givenDiningTableAddRequest_whenEmptyList_thenReturnBadRequest() throws Exception {

        //given

        List<DiningTableAddRequest.DiningTables> givenDiningTableList = new ArrayList<>();


        DiningTableAddRequest diningTableAddRequest = DiningTableAddRequest.builder()
                .diningTablesList(givenDiningTableList)
                .build();

        //then

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //verify

        Mockito.verify(diningTableService, Mockito.times(0))
                .save(Mockito.any(DiningTableAddCommand.class));

    }

    @Test
    void givenUpdateDiningTable_whenValidInput_thenReturnSuccess() throws Exception {

        //given

        Long diningTableId = 1L;

        DiningTableUpdateRequest diningTableUpdateRequest = DiningTableUpdateRequest.builder()
                .size(4)
                .status(DiningTableStatus.OCCUPIED)
                .build();

        //when

        DiningTable diningTable = DiningTable.builder()
                .id(1L)
                .size(1)
                .status(DiningTableStatus.AVAILABLE)
                .build();


        //Then


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", diningTableId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify

        Mockito.verify(diningTableService, Mockito.times(1))
                .update(Mockito.any(Long.class), Mockito.any(DiningTableUpdateCommand.class));

    }

    @Test
    void givenUpdateDiningTable_whenInvalidId_thenReturnSuccess() throws Exception {

        //given

        Long diningTableId = -1L;

        DiningTableUpdateRequest diningTableUpdateRequest = DiningTableUpdateRequest.builder()
                .size(4)
                .status(DiningTableStatus.OCCUPIED)
                .build();

        //when


        //Then


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", diningTableId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


        // Verify

        Mockito.verify(diningTableService, Mockito.times(0))
                .update(Mockito.any(Long.class), Mockito.any(DiningTableUpdateCommand.class));

    }

    @Test
    void givenUpdateDiningTable_whenInvalidSize_thenReturnSuccess() throws Exception {

        //given

        Long diningTableId = 1L;

        DiningTableUpdateRequest diningTableUpdateRequest = DiningTableUpdateRequest.builder()
                .size(-4)
                .status(DiningTableStatus.OCCUPIED)
                .build();

        //when


        //Then


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", diningTableId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


        // Verify

        Mockito.verify(diningTableService, Mockito.times(0))
                .update(Mockito.any(Long.class), Mockito.any(DiningTableUpdateCommand.class));

    }

    @Test
    void givenDeleteDiningTableId_whenValidInput_thenReturnBadRequest() throws Exception {

        //Given

        Long diningTableId = 1L;

        //Then

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", diningTableId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify

        Mockito.verify(diningTableService, Mockito.times(1)).deleteById(diningTableId);

    }

    @Test
    void givenDeleteDiningTableId_whenInValidInput_thenReturnBadRequest() throws Exception {

        //Given

        Long diningTableId = -1L;

        //Then

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", diningTableId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Verify

        Mockito.verify(diningTableService, Mockito.times(0)).deleteById(diningTableId);

    }

}