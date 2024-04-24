package cod.restaurantapi.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageList<T> {

    private List<T> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPageCount;

    private Long totalElementCount;

    private RMAFilter filteredBy;

    private Sorting sortedBy;

}
