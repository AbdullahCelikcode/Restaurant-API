package cod.restaurantapi.parameter.repository;

import cod.restaurantapi.parameter.repository.entity.RMAParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RMAParameterRepository extends JpaRepository<RMAParameterEntity, Integer> {
    Optional<RMAParameterEntity> findByName(String name);
}
