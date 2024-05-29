package cod.restaurantapi.diningtable.repository;

import cod.restaurantapi.diningtable.repository.entity.DiningTableEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public interface DiningTableTestRepository extends DiningTableRepository {

    Optional<DiningTableEntity> findBymergeId(UUID mergeId);

}
