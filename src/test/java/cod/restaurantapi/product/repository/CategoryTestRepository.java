package cod.restaurantapi.product.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public interface CategoryTestRepository extends CategoryRepository {

    Boolean existsByName(String name);


}
