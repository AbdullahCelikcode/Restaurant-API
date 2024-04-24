package cod.restaurantapi.common.util;

import org.springframework.data.jpa.domain.Specification;

public interface RomaSpecification {
    <C> Specification<C> toSpecification(final Class<C> clazz);
}
