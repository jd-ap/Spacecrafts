package travel.w2m.techproof.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import travel.w2m.techproof.model.SpacecraftCommandDto;
import travel.w2m.techproof.model.SpacecraftDto;
import travel.w2m.techproof.service.SpacecraftsService;
import travel.w2m.techproof.web.SpacecraftApi;
import travel.w2m.techproof.web.WebMapper;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SpacecraftsController implements SpacecraftApi {

    private final SpacecraftsService spacecraftsService;

    @Override
    public ResponseEntity<Void> createShip(SpacecraftCommandDto spacecraftCommandDto) {
        var spacecraft = WebMapper.requestBodyToEntity(spacecraftCommandDto);
        var created = spacecraftsService.createOne(spacecraft);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteShipById(Integer idShip) {
        spacecraftsService.deleteOneById(idShip);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<SpacecraftDto>> findAllShips(Integer pageNumber, Integer pageSize, List<String> sort) {
        var pageable = WebMapper.newPageRequest(pageNumber, pageSize, sort);
        var page = spacecraftsService.findAll(pageable);

        return WebMapper.toPageResponse(page);
    }

    @Override
    public ResponseEntity<SpacecraftDto> getShipById(Integer idShip) {
        return spacecraftsService.findOneById(idShip)
                .map(WebMapper::entityToResponseBody)
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

    @Override
    public ResponseEntity<List<SpacecraftDto>> getShipsByName(String nameShip, Integer pageNumber, Integer pageSize, List<String> sort) {
        var pageable = WebMapper.newPageRequest(pageNumber, pageSize, sort);
        var page = spacecraftsService.findAllByNameThatContain(nameShip, pageable);

        return WebMapper.toPageResponse(page);
    }

    @Override
    public ResponseEntity<Void> updateShipById(Integer idShip, SpacecraftCommandDto spacecraftCommandDto) {
        var spacecraft = WebMapper.requestBodyToEntity(spacecraftCommandDto);
        var updated = spacecraftsService.modifyOneById(idShip, spacecraft);

        return ResponseEntity.ok().build();
    }

}
