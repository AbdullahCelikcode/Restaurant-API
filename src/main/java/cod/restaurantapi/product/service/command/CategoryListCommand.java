package cod.restaurantapi.product.service.command;

import cod.restaurantapi.common.model.ListCommand;
import cod.restaurantapi.common.model.RMASpecification;
import cod.restaurantapi.product.util.CategoryFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Getter
@Setter
@SuperBuilder
public class CategoryListCommand extends ListCommand implements RMASpecification {

    private CategoryFilter filter;


    @Override
    public <C> Specification<C> toSpecification(Class<C> clazz) {

        if (this.filter == null) {
            return Specification.allOf();
        }

        Specification<C> specification = Specification.where(null);

        if (StringUtils.hasText(this.filter.getName())) {
            Specification<C> tempSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), STR."%\{filter.getName().toLowerCase()}%");
            specification = specification.and(tempSpecification);
        }

        if (!CollectionUtils.isEmpty(this.filter.getStatuses())) {
            Specification<C> statusSpecification = (root, _, _) ->
                    root.get("status").in(this.filter.getStatuses());
            specification = specification.and(statusSpecification);
        }

        return specification;

    }

}
