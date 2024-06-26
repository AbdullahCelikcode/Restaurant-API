package cod.restaurantapi.diningtable.service.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import cod.restaurantapi.diningtable.service.command.DiningTableUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiningTableUpdateCommandToDiningTableEntityMapper extends BaseMapper<DiningTableUpdateCommand, DiningTableEntity> {

    void update(@MappingTarget DiningTableUpdateCommand target, DiningTableEntity source);

    DiningTableUpdateCommandToDiningTableEntityMapper INSTANCE = Mappers.getMapper(DiningTableUpdateCommandToDiningTableEntityMapper.class);
}
