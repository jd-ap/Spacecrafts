package travel.w2m.techproof.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.w2m.techproof.entity.Spacecraft;

@Repository
public interface SpacecraftsRepository extends JpaRepository<Spacecraft, Integer> {

    Page<Spacecraft> findAllActives(Pageable pageable);

    Page<Spacecraft> findAllByName(String name, Pageable pageable);

}
