package cod.restaurantapi.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RMAPageResponse<T> {

    private List<T> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPageCount;

    private Long totalElementCount;

    private RMAFilter filteredBy;

    private Sorting sortedBy;

    @SuppressWarnings("unused")
    public static class RMAPageResponseBuilder<T> {
        private RMAPageResponseBuilder() {

        }

        public <Z> RMAPageResponseBuilder<T> page(Page<Z> page) {
            this
                    .pageNumber(page.getNumber() + 1)
                    .pageSize(page.getSize())
                    .totalPageCount(page.getTotalPages())
                    .totalElementCount(page.getTotalElements());

            return this;

        }
    }
}


