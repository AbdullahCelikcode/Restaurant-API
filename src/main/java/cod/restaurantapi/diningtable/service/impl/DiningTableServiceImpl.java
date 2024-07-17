package cod.restaurantapi.diningtable.service.impl;

import cod.restaurantapi.common.exception.RMAStatusAlreadyChangedException;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableAlreadySplitException;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableNotExistException;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableStatusIsNotValid;
import cod.restaurantapi.diningtable.controller.exceptions.MergeNotExistException;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import cod.restaurantapi.diningtable.repository.DiningTableRepository;
import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import cod.restaurantapi.diningtable.service.DiningTableService;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableMergeCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableStatusCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import cod.restaurantapi.diningtable.service.domain.DiningTable;
import cod.restaurantapi.diningtable.service.mapper.DiningTableEntityToDiningTableMapper;
import cod.restaurantapi.diningtable.service.mapper.DiningTableUpdateCommandToDiningTableEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiningTableServiceImpl implements DiningTableService {

    private final DiningTableRepository diningTableRepository;

    private static final DiningTableEntityToDiningTableMapper diningTableEntityToDiningTableMapper = DiningTableEntityToDiningTableMapper.INSTANCE;
    private static final DiningTableUpdateCommandToDiningTableEntityMapper diningTableUpdateCommandToDiningTableEntityMapper = DiningTableUpdateCommandToDiningTableEntityMapper.INSTANCE;


    @Override
    public DiningTable findById(Long id) {
        DiningTableEntity diningTableEntity = diningTableRepository.findById(id).orElseThrow(DiningTableNotExistException::new);

        return diningTableEntityToDiningTableMapper.map(diningTableEntity);
    }


    @Override
    public RMAPageResponse<DiningTable> findAll(DiningTableListCommand diningTableListCommand) {
        Page<DiningTableEntity> diningTableEntityPage = diningTableRepository.findAll(
                diningTableListCommand.toSpecification(DiningTableEntity.class),
                diningTableListCommand.toPageable());


        return RMAPageResponse.<DiningTable>builder()
                .page(diningTableEntityPage)
                .content(diningTableEntityToDiningTableMapper.map(diningTableEntityPage.getContent()))
                .sortedBy(Sorting.of(diningTableEntityPage.getSort()))
                .filteredBy(diningTableListCommand.getFilter())
                .build();
    }

    @Override
    public void changeStatus(DiningTableStatusCommand diningTableStatusCommand, Long id) {

        DiningTableEntity diningTableEntity = diningTableRepository.findById(id).orElseThrow(DiningTableNotExistException::new);
        this.checkIfStatusChanged(diningTableEntity.getStatus(), diningTableStatusCommand.getStatus());
        diningTableEntity.setStatus(diningTableStatusCommand.getStatus());

        diningTableRepository.save(diningTableEntity);
    }

    @Override
    public void mergeDiningTables(DiningTableMergeCommand diningTableMergeCommand) {

        List<DiningTableEntity> diningTableEntityList = diningTableRepository.findAllById(diningTableMergeCommand.getIds());

        for (DiningTableEntity diningTableEntity : diningTableEntityList) {
            if (diningTableEntity.getStatus() != DiningTableStatus.AVAILABLE) {
                throw new DiningTableStatusIsNotValid();
            }
        }
        UUID mergeId = UUID.randomUUID();

        for (DiningTableEntity diningTableEntity : diningTableEntityList) {
            diningTableEntity.setMergeId(mergeId);
            diningTableEntity.setStatus(DiningTableStatus.OCCUPIED);
        }
        diningTableRepository.saveAll(diningTableEntityList);

    }

    @Override
    public void splitDiningTables(UUID mergeId) {
        List<DiningTableEntity> diningTableEntityList = diningTableRepository.findByMergeId(mergeId);

        if (diningTableEntityList == null || diningTableEntityList.isEmpty()) {
            throw new MergeNotExistException();
        }
        if (diningTableEntityList.size() == 1) {
            throw new DiningTableAlreadySplitException();
        }

        for (DiningTableEntity diningTableEntity : diningTableEntityList) {
            if (diningTableEntity.getStatus() == DiningTableStatus.TAKING_ORDERS) {
                throw new DiningTableStatusIsNotValid();
            }
        }

        for (DiningTableEntity diningTableEntity : diningTableEntityList) {
            diningTableEntity.setMergeId(UUID.randomUUID());
            diningTableEntity.setStatus(DiningTableStatus.AVAILABLE);
        }
        diningTableRepository.saveAll(diningTableEntityList);
    }


    @Override
    public void save(DiningTableAddCommand diningTableAddCommand) {
        List<DiningTableEntity> diningTableEntityList = new ArrayList<>();
        diningTableAddCommand.getDiningTablesList()
                .forEach(diningTables -> {
                    for (int i = 0; i < diningTables.getCount(); i++) {
                        diningTableEntityList.add(
                                DiningTableEntity.builder()
                                        .size(diningTables.getSize())
                                        .build());
                    }
                });
        diningTableRepository.saveAll(diningTableEntityList);
    }


    @Override
    public void update(Long id, DiningTableUpdateCommand diningTableUpdateCommand) {

        DiningTableEntity diningTableEntity = diningTableRepository.findById(id).orElseThrow(DiningTableNotExistException::new);

        diningTableUpdateCommandToDiningTableEntityMapper.update(diningTableUpdateCommand, diningTableEntity);
        diningTableRepository.save(diningTableEntity);

    }


    @Override
    public void deleteById(Long id) {
        DiningTableEntity diningTableEntity = diningTableRepository.findById(id).orElseThrow(DiningTableNotExistException::new);

        this.checkIfStatusChanged(diningTableEntity.getStatus(), DiningTableStatus.DELETED);

        diningTableEntity.setStatus(DiningTableStatus.DELETED);

        diningTableRepository.save(diningTableEntity);
    }

    private void checkIfStatusChanged(DiningTableStatus entityStatus, DiningTableStatus status) {
        if (entityStatus == status) {
            throw new RMAStatusAlreadyChangedException();
        }
    }

}
