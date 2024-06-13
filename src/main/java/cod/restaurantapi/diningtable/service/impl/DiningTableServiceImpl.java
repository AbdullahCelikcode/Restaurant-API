package cod.restaurantapi.diningtable.service.impl;

import cod.restaurantapi.common.exception.RMAStatusAlreadyChangedException;
import cod.restaurantapi.common.model.RMAPageResponse;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.diningtable.controller.exceptions.DiningTableNotExistException;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import cod.restaurantapi.diningtable.repository.DiningTableRepository;
import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import cod.restaurantapi.diningtable.service.DiningTableService;
import cod.restaurantapi.diningtable.service.command.DiningTableAddCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableListCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommandToDiningTableEntityMapper;
import cod.restaurantapi.diningtable.service.domain.DiningTable;
import cod.restaurantapi.diningtable.service.mapper.DiningTableEntityToDiningTableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
