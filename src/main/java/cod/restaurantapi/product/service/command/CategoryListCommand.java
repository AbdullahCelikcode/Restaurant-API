package cod.restaurantapi.product.service.command;

import cod.restaurantapi.common.util.Pagination;
import cod.restaurantapi.common.util.RomaSpecification;
import cod.restaurantapi.common.util.Sorting;
import cod.restaurantapi.product.util.CategoryFilter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Builder
public class CategoryListCommand implements RomaSpecification {

    private Pagination pagination;

    private CategoryFilter filter;

    private Sorting sort;

    @Override
    public <C> Specification<C> toSpecification(Class<C> clazz) {


        if (this.filter == null) {
            return Specification.allOf();
        }

        Specification<C> specification = Specification.where(null);

        if (StringUtils.hasText(this.filter.getName())) {
            Specification<C> tempSpecification = (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), STR."%\{filter.getName().toLowerCase()}%");
            specification = specification.and(tempSpecification);
        }

        if (!CollectionUtils.isEmpty(this.filter.getStatuses())) {
            Specification<C> statusSpecification = (root, query, criteriaBuilder) ->
                    root.get("status").in(this.filter.getStatuses());
            specification = specification.and(statusSpecification);
        }

        return specification;


    }
}
