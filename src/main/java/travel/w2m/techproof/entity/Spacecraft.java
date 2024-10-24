package travel.w2m.techproof.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SPACECRAFTS")
@NamedQuery(name = "Spacecraft.findAllActives", query = "FROM Spacecraft s WHERE s.active")
@NamedQuery(name = "Spacecraft.findById", query = "FROM Spacecraft s WHERE s.id = :id")
@NamedQuery(name = "Spacecraft.findAllByName", query = "FROM Spacecraft s WHERE LOWER(s.name) LIKE CONCAT('%',LOWER(:name),'%') and s.active")
@NamedQuery(name = "Spacecraft.deleteById", query = "UPDATE Spacecraft s SET s.active = false WHERE s.id = :id")
public class Spacecraft {
    @Id
    @GeneratedValue(generator = "spacecraftSeq")
    @SequenceGenerator(name = "spacecraftSeq", sequenceName = "spacecraft_seq", allocationSize = 1)
    private Integer id;
    @Column(nullable = false, length = 64)
    private String name;
    @Builder.Default
    private boolean active = Boolean.TRUE;

}
