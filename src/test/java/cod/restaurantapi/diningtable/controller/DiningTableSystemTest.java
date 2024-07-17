package cod.restaurantapi.diningtable.controller;

import cod.restaurantapi.RMASystemTest;
import cod.restaurantapi.category.controller.exceptions.CategoryNotFoundException;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.diningtable.controller.request.DiningTableAddRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableListRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableMergeRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableSplitRequest;
import cod.restaurantapi.diningtable.controller.request.DiningTableUpdateRequest;
import cod.restaurantapi.diningtable.controller.response.DiningTableStatusRequest;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import cod.restaurantapi.diningtable.repository.DiningTableTestRepository;
import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class DiningTableSystemTest extends RMASystemTest {

    @Autowired
    protected DiningTableTestRepository diningTableTestRepository;

    private static final String BASE_URL = "/api/v1/dining-table";

    @Test
    void givenGetDiningTableId_thenReturnDiningTable() throws Exception {
        UUID mergeId = UUID.randomUUID();
        DiningTableEntity createdDiningTableEntity = DiningTableEntity.builder()
                .status(DiningTableStatus.AVAILABLE)
                .size(4)
                .mergeId(mergeId)
                .build();
        diningTableTestRepository.save(createdDiningTableEntity);

        DiningTableEntity diningTableEntity = diningTableTestRepository.findBymergeId(mergeId).get();
        Long diningTableId = createdDiningTableEntity.getId();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", diningTableId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(diningTableEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.size").value(diningTableEntity.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status").value(diningTableEntity.getStatus()
                        .toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

        diningTableTestRepository.deleteById(diningTableId);
    }

    @Test
    void givenDiningTableListRequest_thenReturnDiningTableList() throws Exception {

        Pagination pagination = Pagination.builder()
                .pageSize(1)
                .pageNumber(1)
                .build();
        Sorting sorting = Sorting.builder()
                .property("size")
                .direction(Sort.Direction.ASC)
                .build();
        DiningTableListRequest.DiningTableFilter diningTableFilter = DiningTableListRequest.DiningTableFilter.builder()
                .size(4)
                .status(Collections.singleton(DiningTableStatus.AVAILABLE))
                .build();


        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .filter(diningTableFilter)
                .sorting(sorting)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.size")
                        .value(diningTableListRequest.getFilter().getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenDiningTableListRequestWithOutFilter_thenReturnDiningTableList() throws Exception {

        Pagination pagination = Pagination.builder()
                .pageSize(1)
                .pageNumber(1)
                .build();
        Sorting sorting = Sorting.builder()
                .property("size")
                .direction(Sort.Direction.ASC)
                .build();


        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .sorting(sorting)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenDiningTableListRequestWithOutSorting_thenReturnDiningTableList() throws Exception {

        Pagination pagination = Pagination.builder()
                .pageSize(1)
                .pageNumber(1)
                .build();
        DiningTableListRequest.DiningTableFilter diningTableFilter = DiningTableListRequest.DiningTableFilter.builder()
                .size(4)
                .status(Collections.singleton(DiningTableStatus.AVAILABLE))
                .build();

        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .filter(diningTableFilter)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.filteredBy.size")
                        .value(diningTableListRequest.getFilter().getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }

    @Test
    void givenDiningTableListRequestWithOutSortingAndFiltering_thenReturnDiningTableList() throws Exception {

        Pagination pagination = Pagination.builder()
                .pageSize(1)
                .pageNumber(1)
                .build();

        DiningTableListRequest diningTableListRequest = DiningTableListRequest.builder()
                .pagination(pagination)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dining-tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableListRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageNumber")
                        .value(diningTableListRequest.getPagination().getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageSize")
                        .value(diningTableListRequest.getPagination().getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"));

    }


    @Test
    void givenDiningTableAddRequest_thenAddDiningTable() throws Exception {
        List<DiningTableAddRequest.DiningTables> diningTablesList = new ArrayList<>();
        diningTablesList.add(DiningTableAddRequest.DiningTables.builder()
                .size(3)
                .count(5)
                .build());

        DiningTableAddRequest diningTableAddRequest = DiningTableAddRequest.builder()
                .diningTablesList(diningTablesList)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableAddRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void givenDiningTableUpdateRequestAndDiningTableId_thenUpdateDiningTable() throws Exception {
        UUID mergeId = UUID.randomUUID();
        DiningTableEntity diningTableEntity = DiningTableEntity.builder()
                .size(21)
                .status(DiningTableStatus.AVAILABLE)
                .mergeId(mergeId)
                .build();


        diningTableTestRepository.save(diningTableEntity);

        Long diningTableId = diningTableTestRepository.findBymergeId(mergeId).get().getId();


        DiningTableUpdateRequest diningTableUpdateRequest = DiningTableUpdateRequest.builder()
                .size(15)
                .status(DiningTableStatus.OCCUPIED)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}", diningTableId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        diningTableTestRepository.deleteById(diningTableId);

    }


    @Test
    void givenDeleteDiningTableId_thenDeleteDiningTable() throws Exception {
        UUID mergeId = UUID.randomUUID();
        DiningTableEntity createdDiningTableEntity = DiningTableEntity.builder()
                .status(DiningTableStatus.AVAILABLE)
                .size(12)
                .mergeId(mergeId)
                .build();
        diningTableTestRepository.save(createdDiningTableEntity);

        Long diningTableId = diningTableTestRepository.findBymergeId(createdDiningTableEntity.getMergeId()).get().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/{id}", diningTableId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        DiningTableEntity diningTable = diningTableTestRepository.findById(diningTableId)
                .orElseThrow(CategoryNotFoundException::new);

        Assertions.assertEquals(DiningTableStatus.DELETED, diningTable.getStatus());

        diningTableTestRepository.deleteById(diningTableId);


    }

    @Test
    void givenChangeStatusDiningTableId_thenChangeStatus() throws Exception {

        // initialize
        UUID mergeId = UUID.randomUUID();
        DiningTableEntity createdDiningTableEntity = DiningTableEntity.builder()
                .status(DiningTableStatus.AVAILABLE)
                .size(4)
                .mergeId(mergeId)
                .build();
        diningTableTestRepository.save(createdDiningTableEntity);

        Long diningTableId = diningTableTestRepository.findBymergeId(createdDiningTableEntity.getMergeId()).get().getId();

        // given

        DiningTableStatusRequest diningTableStatusRequest = DiningTableStatusRequest.builder()
                .status(DiningTableStatus.OCCUPIED)
                .build();

        //when

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/{id}/status", diningTableId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableStatusRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        DiningTableEntity diningTable = diningTableTestRepository.findById(diningTableId)
                .orElseThrow(CategoryNotFoundException::new);

        //verify

        Assertions.assertEquals(diningTableStatusRequest.getStatus(), diningTable.getStatus());

        // delete
        diningTableTestRepository.deleteById(diningTableId);


    }

    @Test
    void givenDiningTableMerge_thenMergeTables() throws Exception {

        // initialize
        UUID mergeId1 = UUID.randomUUID();
        DiningTableEntity createdDiningTableEntity = DiningTableEntity.builder()
                .status(DiningTableStatus.AVAILABLE)
                .size(4)
                .mergeId(mergeId1)
                .build();
        UUID mergeId2 = UUID.randomUUID();
        DiningTableEntity createdDiningTableEntity2 = DiningTableEntity.builder()
                .status(DiningTableStatus.AVAILABLE)
                .size(4)
                .mergeId(mergeId2)
                .build();
        diningTableTestRepository.save(createdDiningTableEntity);
        diningTableTestRepository.save(createdDiningTableEntity2);

        Long diningTableId = diningTableTestRepository.findBymergeId(createdDiningTableEntity.getMergeId()).get().getId();
        Long diningTableId2 = diningTableTestRepository.findBymergeId(createdDiningTableEntity2.getMergeId()).get().getId();

        // given
        List<Long> diningTableIds = List.of(diningTableId, diningTableId2);
        DiningTableMergeRequest diningTableMergeRequest = DiningTableMergeRequest.builder()
                .ids(diningTableIds)
                .build();

        //when

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/merge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableMergeRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        DiningTableEntity diningTable = diningTableTestRepository.findById(diningTableId)
                .orElseThrow(CategoryNotFoundException::new);
        DiningTableEntity diningTable2 = diningTableTestRepository.findById(diningTableId)
                .orElseThrow(CategoryNotFoundException::new);

        //verify

        Assertions.assertEquals(diningTable.getMergeId(), diningTable2.getMergeId());

        // delete
        diningTableTestRepository.deleteById(diningTableId);
        diningTableTestRepository.deleteById(diningTableId2);


    }

    @Test
    void givenDiningTableSplit_thenSplitTables() throws Exception {

        // given

        UUID mergeId = UUID.randomUUID();
        DiningTableSplitRequest diningTableSplitRequest = DiningTableSplitRequest.builder()
                .mergeId(mergeId)
                .build();

        // initialize
        DiningTableEntity createdDiningTableEntity = DiningTableEntity.builder()
                .status(DiningTableStatus.OCCUPIED)
                .size(4)
                .mergeId(mergeId)
                .build();
        DiningTableEntity createdDiningTableEntity2 = DiningTableEntity.builder()
                .status(DiningTableStatus.OCCUPIED)
                .size(4)
                .mergeId(mergeId)
                .build();
        diningTableTestRepository.save(createdDiningTableEntity);
        diningTableTestRepository.save(createdDiningTableEntity2);

        List<DiningTableEntity> diningTableEntityList = diningTableTestRepository.findByMergeId(mergeId);


        //when

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/split")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(diningTableSplitRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        DiningTableEntity diningTable = diningTableTestRepository.findById(diningTableEntityList.get(0).getId())
                .orElseThrow(CategoryNotFoundException::new);
        DiningTableEntity diningTable2 = diningTableTestRepository.findById(diningTableEntityList.get(1).getId())
                .orElseThrow(CategoryNotFoundException::new);

        //verify

        Assertions.assertNotEquals(diningTable.getMergeId(), diningTable2.getMergeId());

        // delete
        diningTableTestRepository.deleteById(diningTable.getId());
        diningTableTestRepository.deleteById(diningTable2.getId());


    }


}
