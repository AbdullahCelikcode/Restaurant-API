package cod.restaurantapi.diningtable.service.impl;

import cod.restaurantapi.RMAServiceTest;
import cod.restaurantapi.common.exception.RMAStatusAlreadyChangedException;
import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableAlreadySplitException;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableNotExistException;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableStatusIsNotValid;
import cod.restaurantapi.diningtable.controller.exceptions.MergeNotExistException;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import cod.restaurantapi.diningtable.repository.DiningTableRepository;
import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableMergeCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableStatusCommand;
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
import java.util.UUID;

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

        diningTableService.update(diningTableId, diningTableUpdateCommand);

        //verify

        Mockito.verify(diningTableRepository, Mockito.times(1)).findById(Mockito.any(Long.class));


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

    @Test
    void givenChangeStatus_whenTableStatusIsChanging_thenReturnSuccess() {

        // given
        Long diningTableId = 1L;


        DiningTableStatusCommand diningTableStatusCommand = DiningTableStatusCommand.builder()
                .status(DiningTableStatus.AVAILABLE)
                .build();
        // when

        DiningTableEntity diningTableEntity = DiningTableEntity.builder()
                .id(diningTableId)
                .mergeId(UUID.randomUUID())
                .size(4)
                .status(DiningTableStatus.RESERVED)
                .build();

        Mockito.when(diningTableRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(diningTableEntity));

        // then

        diningTableService.changeStatus(diningTableStatusCommand, diningTableId);

        // verify

        Mockito.verify(diningTableRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(diningTableRepository, Mockito.times(1)).save(Mockito.any(DiningTableEntity.class));

    }

    @Test
    void givenChangeStatus_whenTableStatusIsSame_thenReturnException() {

        // given

        Long diningTableId = 1L;

        DiningTableStatusCommand diningTableStatusCommand = DiningTableStatusCommand.builder()
                .status(DiningTableStatus.AVAILABLE)
                .build();
        // when

        DiningTableEntity diningTableEntity = DiningTableEntity.builder()
                .id(diningTableId)
                .mergeId(UUID.randomUUID())
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build();

        Mockito.when(diningTableRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(diningTableEntity));

        // then

        Assertions.assertThrows(RMAStatusAlreadyChangedException.class,
                () -> diningTableService.changeStatus(diningTableStatusCommand, diningTableId));

        // verify

        Mockito.verify(diningTableRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(diningTableRepository, Mockito.times(0)).save(Mockito.any(DiningTableEntity.class));

    }

    @Test
    void givenValidMergeRequest_whenDiningTablesStatusAreValid_thenReturnSuccess() {

        // given
        List<Long> diningTableIds = List.of(1L, 2L);
        DiningTableMergeCommand mergeCommand = DiningTableMergeCommand.builder()
                .ids(diningTableIds)
                .build();

        // when
        DiningTableEntity diningTableEntity1 = DiningTableEntity.builder()
                .id(1L)
                .mergeId(UUID.randomUUID())
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build();
        DiningTableEntity diningTableEntity2 = DiningTableEntity.builder()
                .id(2L)
                .mergeId(UUID.randomUUID())
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build();

        List<DiningTableEntity> diningTableEntities = new ArrayList<>();
        diningTableEntities.add(diningTableEntity1);
        diningTableEntities.add(diningTableEntity2);

        Mockito.when(diningTableRepository.findAllById(Mockito.anyList()))
                .thenReturn(diningTableEntities);

        // then

        diningTableService.mergeDiningTables(mergeCommand);

        // verify

        Mockito.verify(diningTableRepository, Mockito.times(1))
                .saveAll(Mockito.anyList());
    }

    @Test
    void givenValidMergeRequest_whenDiningTablesStatusAreNotValid_thenReturnException() {

        // given
        List<Long> diningTableIds = List.of(1L, 2L);
        DiningTableMergeCommand mergeCommand = DiningTableMergeCommand.builder()
                .ids(diningTableIds)
                .build();

        // when
        DiningTableEntity diningTableEntity1 = DiningTableEntity.builder()
                .id(1L)
                .mergeId(UUID.randomUUID())
                .size(4)
                .status(DiningTableStatus.AVAILABLE)
                .build();
        DiningTableEntity diningTableEntity2 = DiningTableEntity.builder()
                .id(2L)
                .mergeId(UUID.randomUUID())
                .size(4)
                .status(DiningTableStatus.OCCUPIED)
                .build();

        List<DiningTableEntity> diningTableEntities = new ArrayList<>();
        diningTableEntities.add(diningTableEntity1);
        diningTableEntities.add(diningTableEntity2);

        Mockito.when(diningTableRepository.findAllById(Mockito.anyList()))
                .thenReturn(diningTableEntities);

        // then
        Assertions.assertThrows(DiningTableStatusIsNotValid.class,
                () -> diningTableService.mergeDiningTables(mergeCommand));


        // verify

        Mockito.verify(diningTableRepository, Mockito.times(0))
                .saveAll(Mockito.anyList());
    }

    @Test
    void givenValidSplitMergeId_whenDiningTablesMergeIdExist_thenReturnSuccess() {

        // given
        UUID givenMergeId = UUID.fromString("d362cbef-38c7-465c-a6f6-1e6961151578");


        // when
        DiningTableEntity diningTableEntity1 = DiningTableEntity.builder()
                .id(1L)
                .mergeId(givenMergeId)
                .size(4)
                .status(DiningTableStatus.OCCUPIED)
                .build();
        DiningTableEntity diningTableEntity2 = DiningTableEntity.builder()
                .id(2L)
                .mergeId(givenMergeId)
                .size(4)
                .status(DiningTableStatus.OCCUPIED)
                .build();

        List<DiningTableEntity> diningTableEntities = new ArrayList<>();
        diningTableEntities.add(diningTableEntity1);
        diningTableEntities.add(diningTableEntity2);

        Mockito.when(diningTableRepository.findByMergeId(Mockito.any(UUID.class)))
                .thenReturn(diningTableEntities);

        // then

        diningTableService.splitDiningTables(givenMergeId);

        // verify

        Mockito.verify(diningTableRepository, Mockito.times(1))
                .saveAll(Mockito.anyList());
    }

    @Test
    void givenValidSplitMergeId_whenDiningTablesNotMerged_thenReturnException() {

        // given
        UUID givenMergeId = UUID.fromString("d362cbef-38c7-465c-a6f6-1e6961151578");


        // when
        DiningTableEntity diningTableEntity1 = DiningTableEntity.builder()
                .id(1L)
                .mergeId(givenMergeId)
                .size(4)
                .status(DiningTableStatus.OCCUPIED)
                .build();


        List<DiningTableEntity> diningTableEntities = new ArrayList<>();
        diningTableEntities.add(diningTableEntity1);


        Mockito.when(diningTableRepository.findByMergeId(Mockito.any(UUID.class)))
                .thenReturn(diningTableEntities);

        // then
        Assertions.assertThrows(DiningTableAlreadySplitException.class,
                () -> diningTableService.splitDiningTables(givenMergeId));


        // verify

        Mockito.verify(diningTableRepository, Mockito.times(0))
                .saveAll(Mockito.anyList());
    }

    @Test
    void givenValidSplitMergeId_whenMergeIdIsNotExist_thenReturnException() {

        // given
        UUID givenMergeId = UUID.fromString("d362cbef-38c7-465c-a6f6-1e6961151578");


        // when


        Mockito.when(diningTableRepository.findByMergeId(Mockito.any(UUID.class)))
                .thenReturn(null);

        // then
        Assertions.assertThrows(MergeNotExistException.class,
                () -> diningTableService.splitDiningTables(givenMergeId));


        // verify

        Mockito.verify(diningTableRepository, Mockito.times(0))
                .saveAll(Mockito.anyList());
    }


}