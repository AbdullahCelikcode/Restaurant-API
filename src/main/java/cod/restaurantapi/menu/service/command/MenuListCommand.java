package cod.restaurantapi.menu.service.command;

import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.common.model.RMASpecification;
import cod.restaurantapi.common.service.command.RMAListCommand;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@Getter
@SuperBuilder
public class MenuListCommand extends RMAListCommand implements RMASpecification {

    private MenuFilter filter;


    @Getter
    @Builder
    @AllArgsConstructor
    public static class MenuFilter implements RMAFilter {

        private String name;

    }


    @Override
    public <C> Specification<C> toSpecification(Class<C> clazz) {

        Specification<C> specification = Specification.where(null);

        Specification<C> statusSpecification = (root, _, _) ->
                root.get("status").in(ProductStatus.ACTIVE);
        specification = specification.and(statusSpecification);

        if (this.filter != null && StringUtils.hasText(this.filter.getName())) {
            Specification<C> nameSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), STR."%\{filter.getName().toLowerCase()}%");
            specification = specification.and(nameSpecification);
        }


        return specification;
    }
}
