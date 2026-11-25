package ma.edoct.service;

import ma.edoct.dto.CandidatDTO;
import ma.edoct.dto.SujetCreationDTO;
import ma.edoct.dto.SujetDTO;
import ma.edoct.mapper.Mapper;
import ma.edoct.model.Sujet;
import ma.edoct.repository.CandidatureRepository;
import ma.edoct.repository.ProfesseurRepository;
import ma.edoct.repository.SujetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesseurService {
    private final SujetRepository sujetRepository;
    private final CandidatureRepository candidatureRepository;
    private final ProfesseurRepository professeurRepository;
    private final Mapper mapper;

    public ProfesseurService(SujetRepository sujetRepository,
                             CandidatureRepository candidatureRepository,
                             ProfesseurRepository professeurRepository,
                             Mapper mapper) {
        this.sujetRepository = sujetRepository;
        this.candidatureRepository = candidatureRepository;
        this.professeurRepository = professeurRepository;
        this.mapper = mapper;
    }

    public SujetDTO proposerSujet(Long professeurId, SujetCreationDTO dto) {
        professeurRepository.findById(professeurId).orElseThrow();
        Sujet sujet = mapper.toSujet(dto, professeurId);
        return mapper.toDto(sujetRepository.save(sujet));
    }

    public Optional<SujetDTO> modifierSujet(Long professeurId, Long sujetId, SujetCreationDTO dto) {
        return sujetRepository.findById(sujetId)
                .filter(sujet -> professeurId.equals(sujet.getProfesseurId()))
                .map(sujet -> {
                    sujet.setTitre(dto.getTitre());
                    sujet.setDescription(dto.getDescription());
                    sujet.setCoDirecteurId(dto.getCoDirecteurId());
                    sujet.setFormationDoctoraleId(dto.getFormationDoctoraleId());
                    return mapper.toDto(sujetRepository.save(sujet));
                });
    }

    public List<CandidatDTO> consulterCandidats(Long professeurId) {
        professeurRepository.findById(professeurId).orElseThrow();
        return mapper.toCandidatDtos(candidatureRepository.findCandidatsByProfesseurId(professeurId));
    }
}
