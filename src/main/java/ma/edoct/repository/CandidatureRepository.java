package ma.edoct.repository;

import ma.edoct.model.Candidat;
import ma.edoct.model.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByCandidatId(Long candidatId);

    long countByCandidatId(Long candidatId);

    @Query("select distinct c.candidat from Candidature c where c.sujet.professeurId = :professeurId")
    List<Candidat> findCandidatsByProfesseurId(@Param("professeurId") Long professeurId);

    @Modifying
    @Query("delete from Candidature c where c.candidat.id = :candidatId")
    void deleteByCandidatId(@Param("candidatId") Long candidatId);
}
