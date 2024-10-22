package travel.w2m.techproof.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import travel.w2m.techproof.entity.Spacecraft;
import travel.w2m.techproof.repository.SpacecraftsRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpacecraftsServiceTest {

    @InjectMocks
    SpacecraftsService spacecraftsService;

    @Spy
    SpacecraftsRepository spacecraftsRepository;

    @Test
    void givenFindOne_thenReturnsOne() {
        //given
        Integer idShip = 0;
        var expected = Spacecraft.builder().id(idShip).name("tests").active(Boolean.TRUE).build();

        when(spacecraftsRepository.findById(anyInt()))
                .thenReturn(Optional.of(expected));

        //when
        Optional<Spacecraft> result = spacecraftsService.findOneById(idShip);

        //then
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    void givenFindAll_thenReturnsList() {
        //given
        Pageable pageable = PageRequest.of(0,1);
        var shipOne = Spacecraft.builder().id(1).build();
        var shipTwo = Spacecraft.builder().id(2).build();
        var expected = new PageImpl<>(List.of(shipOne, shipTwo), pageable, 2);

        when(spacecraftsRepository.findAll(any(Pageable.class))).thenReturn(expected);

        //when
        var result = spacecraftsService.findAll(pageable);

        //then
        assertFalse(result.isEmpty());
        assertTrue(result.hasNext());
        assertFalse(result.hasPrevious());
        assertEquals(2, result.getTotalPages());
        assertEquals(shipOne, result.stream().findFirst().get());
    }

    @Test
    void givenFindAllByNameThatContain_thenReturnsList() {
        //given
        String name = "wing";
        Pageable pageable = PageRequest.of(0,1);
        var xWingShip = Spacecraft.builder().id(0).name("x-wing").build();
        var expected = new PageImpl<>(List.of(xWingShip), pageable, 1);

        when(spacecraftsRepository.findAllByName(anyString(), any(Pageable.class)))
                .thenReturn(expected);

        //when
        var result = spacecraftsService.findAllByNameThatContain(name, pageable);

        //then
        assertFalse(result.isEmpty());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());
        assertEquals(1, result.getTotalPages());
        assertEquals(xWingShip, result.stream().findFirst().get());
    }

    @Test
    void givenCreateOne_thenReturnsOne() {
        //given
        var ship = Spacecraft.builder().name("x-wing").build();
        var expected = Spacecraft.builder().id(0).name("x-wing").build();

        when(spacecraftsRepository.save(any(Spacecraft.class))).thenReturn(expected);

        //when
        var result = spacecraftsService.createOne(ship);

        //then
        assertEquals(expected, result);
        verify(spacecraftsRepository, times(1)).save(any());
    }

    @Test
    void givenModifyOneById_thenReturnsOne() {
        //given
        var idShip = 0;
        var ship = Spacecraft.builder().name("x-wing").build();

        var stored = Spacecraft.builder().id(idShip).name("gniw-x").active(Boolean.FALSE).build();
        var expected = Spacecraft.builder().id(idShip).name("x-wing").active(Boolean.FALSE).build();

        when(spacecraftsRepository.findById(anyInt())).thenReturn(Optional.of(stored));
        when(spacecraftsRepository.save(any(Spacecraft.class))).thenReturn(expected);

        //when
        var result = spacecraftsService.modifyOneById(idShip, ship);

        //then
        assertEquals(expected, result);
        verify(spacecraftsRepository, times(1)).findById(idShip);
        verify(spacecraftsRepository, times(1)).save(any());
    }

    @Test
    void givenDeleteOne_thenReturnsOK() {
        //given
        var idShip = 0;

        doNothing().when(spacecraftsRepository).deleteById(anyInt());

        //when
        spacecraftsService.deleteOneById(idShip);

        //then
        verify(spacecraftsRepository, times(1)).deleteById(idShip);
    }
}