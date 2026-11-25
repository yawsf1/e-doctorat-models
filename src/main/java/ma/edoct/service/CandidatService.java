package ma.edoct.service;

import ma.edoct.dto.CandidatureDTO;
import ma.edoct.dto.CandidatDTO;
import ma.edoct.mapper.Mapper;
import ma.edoct.model.Calendrier;
import ma.edoct.model.Candidat;
import ma.edoct.model.Candidature;
import ma.edoct.model.Sujet;
import ma.edoct.repository.CalendrierRepository;
import ma.edoct.repository.CandidatRepository;
import ma.edoct.repository.CandidatureRepository;
import ma.edoct.repository.SujetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class CandidatService {
    private static final int MAX_SUJETS = 3;
    private static final String POUR_CANDIDATURE = "candidature";

    private final CandidatRepository candidatRepository;
    private final CandidatureRepository candidatureRepository;
    private final SujetRepository sujetRepository;
    private final CalendrierRepository calendrierRepository;
    private final Mapper mapper;

    public CandidatService(CandidatRepository candidatRepository,
                           CandidatureRepository candidatureRepository,
                           SujetRepository sujetRepository,
                           CalendrierRepository calendrierRepository,
                           Mapper mapper) {
        this.candidatRepository = candidatRepository;
        this.candidatureRepository = candidatureRepository;
        this.sujetRepository = sujetRepository;
        this.calendrierRepository = calendrierRepository;
        this.mapper = mapper;
    }

    public Optional<CandidatDTO> findById(Long id) {
        return candidatRepository.findById(id).map(mapper::toDto);
    }

    @Transactional
    public List<CandidatureDTO> candidater(Long candidatId, List<Long> sujetIds, String pathFile) {
        verifierDelai();
        Candidat candidat = candidatRepository.findById(candidatId).orElseThrow();

        List<Long> ids = sujetIds == null ? List.of() : sujetIds.stream().distinct().collect(toList());
        verifierNombreSujets(ids);
        if (candidatureRepository.countByCandidatId(candidatId) + ids.size() > MAX_SUJETS) {
            throw new IllegalArgumentException("Un candidat ne peut postuler qu'à 3 sujets au maximum");
        }
        List<Long> dejaPostules = candidatureRepository.findByCandidatId(candidatId).stream()
                .map(Candidature::getSujetId)
                .collect(toList());
        if (ids.stream().anyMatch(dejaPostules::contains)) {
            throw new IllegalArgumentException("Le candidat a déjà postulé à l'un de ces sujets");
        }

        return enregistrer(candidat, ids, pathFile, false);
    }

    @Transactional
    public List<CandidatureDTO> modifierCandidatures(Long candidatId, List<Long> sujetIds, String pathFile) {
        verifierDelai();
        Candidat candidat = candidatRepository.findById(candidatId).orElseThrow();

        List<Long> ids = sujetIds == null ? List.of() : sujetIds.stream().distinct().collect(toList());
        verifierNombreSujets(ids);

        return enregistrer(candidat, ids, pathFile, true);
    }

    private List<CandidatureDTO> enregistrer(Candidat candidat, List<Long> ids, String pathFile, boolean remplacer) {
        List<Sujet> sujets = sujetRepository.findAllById(ids);
        if (sujets.size() != ids.size()) {
            throw new IllegalArgumentException("Un ou plusieurs sujets demandés n'existent pas");
        }
        if (remplacer) {
            candidatureRepository.deleteByCandidatId(candidat.id);
        }
        List<Candidature> candidatures = sujets.stream()
                .map(sujet -> new Candidature(candidat, sujet, pathFile))
                .collect(toList());
        return mapper.toCandidatureDtos(candidatureRepository.saveAll(candidatures));
    }

    private void verifierNombreSujets(List<Long> ids) {
        if (ids.isEmpty() || ids.size() > MAX_SUJETS) {
            throw new IllegalArgumentException("Il faut choisir entre 1 et 3 sujets");
        }
    }

    private void verifierDelai() {
        Calendrier calendrier = calendrierRepository
                .findFirstByPourOrderByDateFinDesc(POUR_CANDIDATURE)
                .orElseThrow(() -> new IllegalStateException("Aucun délai de candidature configuré"));
        if (LocalDate.now().isAfter(calendrier.getDateFin())) {
            throw new IllegalStateException("Le délai de candidature est fermé");
        }
    }
}
