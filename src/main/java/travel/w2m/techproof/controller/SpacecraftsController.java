package travel.w2m.techproof.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import travel.w2m.techproof.model.SpacecraftCommandDto;
import travel.w2m.techproof.model.SpacecraftDto;
import travel.w2m.techproof.service.SpacecraftsService;
import travel.w2m.techproof.web.SpacecraftApi;
import travel.w2m.techproof.web.SpacecraftMapper;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SpacecraftsController implements SpacecraftApi {

    private final SpacecraftsService spacecraftsService;

    @Override
    public ResponseEntity<Void> createShip(SpacecraftCommandDto spacecraftCommandDto) {
        var spacecraft = SpacecraftMapper.requestBodyToEntity(spacecraftCommandDto);
        var created = spacecraftsService.createOne(spacecraft);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteShipById(Integer idShip) {
        spacecraftsService.deleteOneById(idShip);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<SpacecraftDto>> findAllShips() {
        var pageable = Pageable.ofSize(1);
        var page = spacecraftsService.findAll(pageable);
        var spacecrafts = page.get().map(SpacecraftMapper::entityToResponseBody).toList();
        var headers = Map.of("total-elements", page.getTotalElements(),
                "total-pages", page.getTotalPages(),
                "page-number", page.getNumber(),
                "page-size", page.getNumberOfElements());
        return ResponseEntity.ok(spacecrafts);
    }

    @Override
    public ResponseEntity<SpacecraftDto> getShipById(Integer idShip) {
        return spacecraftsService.findOneById(idShip)
                .map(SpacecraftMapper::entityToResponseBody)
                .map(ResponseEntity::ok)
                .get();
    }

    @Override
    public ResponseEntity<List<SpacecraftDto>> getShipsByName(String nameShip) {
        var pageable = Pageable.ofSize(1);
        var page = spacecraftsService.findAllByNameThatContain(nameShip, pageable);
        var spacecrafts = page.get().map(SpacecraftMapper::entityToResponseBody).toList();
        var headers = Map.of("total-elements", page.getTotalElements(),
                "total-pages", page.getTotalPages(),
                "page-number", page.getNumber(),
                "page-size", page.getNumberOfElements());
        return ResponseEntity.ok(spacecrafts);
    }

    @Override
    public ResponseEntity<Void> updateShipById(Integer idShip, SpacecraftCommandDto spacecraftCommandDto) {
        var spacecraft = SpacecraftMapper.requestBodyToEntity(spacecraftCommandDto);
        var updated = spacecraftsService.modifyOneById(idShip, spacecraft);
        return ResponseEntity.ok().build();
    }

}
