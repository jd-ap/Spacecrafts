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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

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

    }
}