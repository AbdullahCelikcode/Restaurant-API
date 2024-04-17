package cod.restaurantapi;

import cod.restaurantapi.category.repository.entity.CategoryEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public interface CategoryTestRepository extends JpaRepository<CategoryEntity, Long> {

    Boolean existsByName(String name);


}
