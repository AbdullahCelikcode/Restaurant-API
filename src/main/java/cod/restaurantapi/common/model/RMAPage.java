package cod.restaurantapi.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RMAPage<T> {

    private List<T> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPageCount;

    private Long totalElementCount;

    private RMAFilter filteredBy;

    private Sorting sortedBy;

    @SuppressWarnings("unused")
    public static class RMAPageBuilder<T> {

        private RMAPageBuilder() {

        }

        public <Z> RMAPageBuilder<T> map(List<T> content, RMAPageResponse<Z> page) {
            this
                    .content(content)
                    .pageSize(page.getPageSize())
                    .pageNumber(page.getPageNumber())
                    .totalPageCount(page.getTotalPageCount())
                    .totalElementCount(page.getTotalElementCount())
                    .sortedBy(page.getSortedBy())
                    .filteredBy(page.getFilteredBy());

            return this;
        }
    }
}
