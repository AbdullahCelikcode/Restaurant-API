package cod.restaurantapi.diningtable.service.command;

import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.common.model.RMASpecification;
import cod.restaurantapi.common.service.command.RMAListCommand;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Getter
@SuperBuilder
public class DiningTableListCommand extends RMAListCommand implements RMASpecification {

    private DiningTableFilter filter;


    @Getter
    @Builder
    @AllArgsConstructor
    public static class DiningTableFilter implements RMAFilter {
        private Integer size;
        private Set<DiningTableStatus> status;
    }


    @Override
    public <C> Specification<C> toSpecification(Class<C> clazz) {

        if (this.filter == null) {
            return Specification.allOf();
        }

        Specification<C> specification = Specification.where(null);

        if ((this.filter.getSize() != null)) {
            Specification<C> statusSpecification = (root, query,criteriaBuilder) ->
                    root.get("size").in(this.filter.getSize());
            specification = specification.and(statusSpecification);
        }

        if (!CollectionUtils.isEmpty(this.filter.getStatus())) {
            Specification<C> statusSpecification = (root, query,criteriaBuilder) ->
                    root.get("status").in(this.filter.getStatus());
            specification = specification.and(statusSpecification);
        }

        return specification;

    }
}
