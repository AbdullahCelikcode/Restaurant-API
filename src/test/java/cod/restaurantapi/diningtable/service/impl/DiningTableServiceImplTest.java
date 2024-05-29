package cod.restaurantapi.diningtable.service.impl;

import cod.restaurantapi.RMAServiceTest;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableNotExistException;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import cod.restaurantapi.diningtable.repository.DiningTableRepository;
import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import cod.restaurantapi.diningtable.service.domain.DiningTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class DiningTableServiceImplTest extends RMAServiceTest {

    @Mock
    private DiningTableRepository diningTableRepository;

    @InjectMocks
    private DiningTableServiceImpl diningTableService;


    @Test
    void givenDiningTableId_whenDiningTableExists_thenReturnDiningTable() {
        // given
        Long diningTableId = 1L;

        //when
        DiningTableEntity givenDiningTableEntity = DiningTableEntity.builder()
                .id(diningTableId)
                .status(DiningTableStatus.AVAILABLE)
                .size(4)
                .build();

        //then

        Mockito.when(diningTableRepository.findById(diningTableId)).thenReturn(Optional.of(givenDiningTableEntity));

        DiningTable exceptedDiningTable = diningTableService.findById(diningTableId);

        Mockito.verify(diningTableRepository, Mockito.atMostOnce()).findById(Mockito.anyLong());

        Assertions.assertEquals(givenDiningTableEntity.getId(), exceptedDiningTable.getId());
        Assertions.assertEquals(givenDiningTableEntity.getStatus(), exceptedDiningTable.getStatus());
        Assertions.assertEquals(givenDiningTableEntity.getSize(), exceptedDiningTable.getSize());

    }


    @Test
    void givenDiningTableId_whenDiningTableNotExists_thenThrowException() {
        // given
        Long diningTableId = 1L;


        //then

        Mockito.when(diningTableRepository.findById(diningTableId)).thenThrow(DiningTableNotExistException.class);

        Assertions.assertThrows(DiningTableNotExistException.class,
                () -> diningTableService.findById(diningTableId));
    }


    @Test
    void givenDiningTableListCommandWithFilterAndSorting_whenDiningTablesExist_thenReturnDiningTableList() {

        //given
        int pageNumber = 1;
        int pageSize = 3;
        DiningTableListCommand givenCategoryListCommand = DiningTableListCommand.builder()
                .filter(DiningTableListCommand.DiningTableFilter
                        .builder()
                        .size(4)
                        .build())
                .pagination(Pagination.builder()
                        .pageNumber(pageSize)
                        .pageSize(pageNumber)
                        .build())
                .sorting(Sorting.builder()
                        .direction(Sort.Direction.ASC)
                        .property("size")
                        .build())
                .build();

        // then

        List<DiningTableEntity> pageList = new ArrayList<>();

        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());
        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());
        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());


        Page<DiningTableEntity> returnedList = new PageImpl<>(pageList);


        Mockito.when(diningTableRepository.findAll(Mockito.any(Specification.class),
                Mockito.any(Pageable.class))).thenReturn(returnedList);

        RMAPageResponse<DiningTable> exceptedList = diningTableService.findAll(givenCategoryListCommand);


        // verify

        Mockito.verify(diningTableRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(diningTableRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getSize(), pageList.get(0).getSize());
        Assertions.assertEquals(exceptedList.getContent().get(1).getSize(), pageList.get(1).getSize());
        Assertions.assertEquals(exceptedList.getContent().get(2).getSize(), pageList.get(2).getSize());


        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), pageList.size());
    }


    @Test
    void givenDiningTableListCommandWithFilter_whenDiningTablesExist_thenReturnDiningTableList() {

        //given
        int pageNumber = 1;
        int pageSize = 3;
        DiningTableListCommand givenCategoryListCommand = DiningTableListCommand.builder()
                .filter(DiningTableListCommand.DiningTableFilter
                        .builder()
                        .size(4)
                        .build())
                .pagination(Pagination.builder()
                        .pageNumber(pageSize)
                        .pageSize(pageNumber)
                        .build())
                .build();

        // then

        List<DiningTableEntity> pageList = new ArrayList<>();

        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());
        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());
        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());


        Page<DiningTableEntity> returnedList = new PageImpl<>(pageList);


        Mockito.when(diningTableRepository.findAll(Mockito.any(Specification.class),
                Mockito.any(Pageable.class))).thenReturn(returnedList);

        RMAPageResponse<DiningTable> exceptedList = diningTableService.findAll(givenCategoryListCommand);


        // verify

        Mockito.verify(diningTableRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(diningTableRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getSize(), pageList.get(0).getSize());
        Assertions.assertEquals(exceptedList.getContent().get(1).getSize(), pageList.get(1).getSize());
        Assertions.assertEquals(exceptedList.getContent().get(2).getSize(), pageList.get(2).getSize());


        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), pageList.size());
    }


    @Test
    void givenDiningTableListCommandWithOutFilterAndSorting_whenDiningTablesExist_thenReturnDiningTableList() {

        //given
        int pageNumber = 1;
        int pageSize = 3;
        DiningTableListCommand givenCategoryListCommand = DiningTableListCommand.builder()
                .pagination(Pagination.builder()
                        .pageNumber(pageSize)
                        .pageSize(pageNumber)
                        .build())
                .build();

        // then

        List<DiningTableEntity> pageList = new ArrayList<>();

        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());
        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());
        pageList.add(
                DiningTableEntity.builder()
                        .size(4)
                        .status(DiningTableStatus.AVAILABLE)
                        .build());


        Page<DiningTableEntity> returnedList = new PageImpl<>(pageList);


        Mockito.when(diningTableRepository.findAll(Mockito.any(Specification.class),
                Mockito.any(Pageable.class))).thenReturn(returnedList);

        RMAPageResponse<DiningTable> exceptedList = diningTableService.findAll(givenCategoryListCommand);


        // verify

        Mockito.verify(diningTableRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Mockito.verify(diningTableRepository, Mockito.never()).findAll();

        Assertions.assertEquals(exceptedList.getContent().get(0).getSize(), pageList.get(0).getSize());
        Assertions.assertEquals(exceptedList.getContent().get(1).getSize(), pageList.get(1).getSize());
        Assertions.assertEquals(exceptedList.getContent().get(2).getSize(), pageList.get(2).getSize());


        Assertions.assertEquals(exceptedList.getPageSize(), pageSize);
        Assertions.assertEquals(exceptedList.getTotalElementCount(), pageList.size());
    }


    @Test
    void givenDiningTableAddCommand_thenAddDiningTables() {

        //given
        List<DiningTableAddCommand.DiningTables> givenDiningTablesList = new ArrayList<>();
        givenDiningTablesList.add(
                DiningTableAddCommand.DiningTables.builder()
                        .size(5)
                        .count(5)
                        .build()
        );
        givenDiningTablesList.add(
                DiningTableAddCommand.DiningTables.builder()
                        .size(4)
                        .count(5)
                        .build()
        );

        DiningTableAddCommand diningTableAddCommand = DiningTableAddCommand.builder()
                .diningTablesList(givenDiningTablesList)
                .build();

        //then

        diningTableService.save(diningTableAddCommand);

        //verify

        Mockito.verify(diningTableRepository, Mockito.times(1)).saveAll(Mockito.any());

    }

    @Test
    void givenUpdateDiningTableAndDiningTableId_whenDiningTableExist_thenReturnDiningTable() {
        //given

        Long diningTableId = 1L;
        DiningTableUpdateCommand diningTableUpdateCommand = DiningTableUpdateCommand.builder()
                .size(4)
                .status(DiningTableStatus.RESERVED)
                .build();

        //when
        DiningTableEntity diningTableEntity = DiningTableEntity.builder()
                .size(2)
                .status(DiningTableStatus.AVAILABLE)
                .build();

        Mockito.when(diningTableRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(diningTableEntity));

        //then

        DiningTable updatedDiningTable = diningTableService.update(diningTableId, diningTableUpdateCommand);

        //verify

        Mockito.verify(diningTableRepository, Mockito.times(1)).findById(Mockito.any(Long.class));

        Assertions.assertEquals(diningTableUpdateCommand.getSize(), updatedDiningTable.getSize());
        Assertions.assertEquals(diningTableUpdateCommand.getStatus(), updatedDiningTable.getStatus());

    }

    @Test
    void givenUpdateDiningTableAndDiningTableId_whenDiningTableNotExist_thenThrowDiningTableNotFoundException() {
        //given

        Long diningTableId = 1L;
        DiningTableUpdateCommand diningTableUpdateCommand = DiningTableUpdateCommand.builder()
                .size(4)
                .status(DiningTableStatus.RESERVED)
                .build();

        //when


        Mockito.when(diningTableRepository.findById(Mockito.any(Long.class)))
                .thenThrow(DiningTableNotExistException.class);

        //then

        Assertions.assertThrows(DiningTableNotExistException.class,
                () -> diningTableService.update(diningTableId, diningTableUpdateCommand));
    }

    @Test
    void givenDeleteDiningTableId_whenDiningTableNotExist_thenThrowDiningTableNotFoundException() {
        //given

        Long diningTableId = 1L;


        //when

        Mockito.when(diningTableRepository.findById(Mockito.any(Long.class)))
                .thenThrow(DiningTableNotExistException.class);

        //then

        Assertions.assertThrows(DiningTableNotExistException.class,
                () -> diningTableService.deleteById(diningTableId));
    }


    @Test
    void givenDeleteDiningTableId_whenDiningTableExist_thenSoftDeleteDiningTable() {
        //given

        Long diningTableId = 1L;

        //when
        DiningTableEntity diningTableEntity = DiningTableEntity.builder()
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build();

        Mockito.when(diningTableRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(diningTableEntity));

        //then
        diningTableService.deleteById(diningTableId);

        //verify
        Mockito.verify(diningTableRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        Mockito.verify(diningTableRepository, Mockito.times(1)).save(Mockito.any(DiningTableEntity.class));

        Assertions.assertEquals(DiningTableStatus.DELETED, diningTableEntity.getStatus());

    }
}