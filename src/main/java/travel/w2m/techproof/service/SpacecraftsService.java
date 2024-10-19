package travel.w2m.techproof.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import travel.w2m.techproof.entity.Spacecraft;
import travel.w2m.techproof.repository.SpacecraftsRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpacecraftsService {

    private final SpacecraftsRepository spacecraftsRepository;

    public Page<Spacecraft> findAll(Pageable pageable) {
        return spacecraftsRepository.findAll(pageable);
    }

    public Optional<Spacecraft> findOneById(Integer id) {
        return spacecraftsRepository.findById(id);
    }

    public Page<Spacecraft> findAllByNameThatContain(String name, Pageable pageable) {
        return spacecraftsRepository.findAllByName(name, pageable);
    }

    public Spacecraft createOne(Spacecraft spacecraft) {
        return spacecraftsRepository.save(spacecraft);
    }

    public Spacecraft modifyOneById(Integer id, Spacecraft spacecraft) {
        return spacecraftsRepository.save(spacecraft);
    }

    public void deleteOneById(Integer id) {
        spacecraftsRepository.deleteById(id);
    }
}