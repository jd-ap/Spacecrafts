package travel.w2m.techproof.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import travel.w2m.techproof.entity.Spacecraft;
import travel.w2m.techproof.model.SpacecraftCommandDto;
import travel.w2m.techproof.model.SpacecraftDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WebMapper {

    private static final Function<Page<Spacecraft>, List<SpacecraftDto>> collectingResponse = page ->
            page.get().map(WebMapper::entityToResponseBody)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
                        if (result.isEmpty()) throw new NoSuchElementException();
                        return result;
                    }));

    private static final Function<Page<Spacecraft>, HttpHeaders> generatePaginationHeaders = page -> {
        var headers = new HttpHeaders();
        headers.add("x-Pagination-Current", String.valueOf(page.getNumber()));
        headers.add("x-Pagination-Size", String.valueOf(page.getNumberOfElements()));
        headers.add("x-Pagination-Total", String.valueOf(page.getTotalPages()));
        headers.add("x-Pagination-hasNext", String.valueOf(page.hasNext()));
        headers.add("x-Pagination-hasPrevious", String.valueOf(page.hasPrevious()));
        return headers;
    };

    public static Pageable newPageRequest(Integer pageNumber, Integer pageSize, List<String> sort) {
        Sort.Order[] orders = (null == sort) ? new Sort.Order[]{}
                : sort.stream()
                .map(it -> it.split(":"))
                .map(it -> {
                    if (it.length == 2 && "d".equalsIgnoreCase(it[1]))
                        return Sort.Order.desc(it[0]);
                    else
                        return Sort.Order.asc(it[0]);
                })
                .toArray(Sort.Order[]::new);

        return PageRequest.of(null == pageNumber || pageNumber <= 0 ? 0 : pageNumber - 1
                , null == pageSize ? 10 : pageSize
                , Sort.by(orders));
    }

    public static Spacecraft requestBodyToEntity(SpacecraftCommandDto spacecraftDto) {
        return Spacecraft.builder()
                .name(spacecraftDto.getName())
                .build();
    }

    public static SpacecraftDto entityToResponseBody(Spacecraft spacecraft) {
        var dto = new SpacecraftDto();
        dto.setId(spacecraft.getId());
        dto.setName(spacecraft.getName());
        dto.setActive(spacecraft.isActive());
        return dto;
    }

    public static ResponseEntity<List<SpacecraftDto>> toPageResponse(Page<Spacecraft> page) {

        var spacecrafts = collectingResponse.apply(page);
        var headers = generatePaginationHeaders.apply(page);

        return new ResponseEntity<>(spacecrafts, headers, HttpStatus.OK);
    }
}
