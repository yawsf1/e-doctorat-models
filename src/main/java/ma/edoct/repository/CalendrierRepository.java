package ma.edoct.repository;

import ma.edoct.model.Calendrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendrierRepository extends JpaRepository<Calendrier, Integer> {
    Optional<Calendrier> findFirstByPourOrderByDateFinDesc(String pour);
}
