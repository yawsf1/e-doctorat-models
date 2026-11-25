package ma.edoct.mapper;

import ma.edoct.dto.CandidatDTO;
import ma.edoct.dto.CandidatureDTO;
import ma.edoct.dto.SujetCreationDTO;
import ma.edoct.dto.SujetDTO;
import ma.edoct.model.Candidat;
import ma.edoct.model.Candidature;
import ma.edoct.model.Sujet;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Mapper {
    public CandidatDTO toDto(Candidat candidat) {
        return new CandidatDTO(
                candidat.id,
                candidat.cne,
                candidat.cin,
                candidat.nomCandidatAr,
                candidat.prenomCandidatAr,
                candidat.ville,
                candidat.telCandidat,
                candidat.pathCv,
                candidat.pathPhoto
        );
    }

    public List<CandidatDTO> toCandidatDtos(List<Candidat> candidats) {
        return candidats.stream().map(this::toDto).collect(toList());
    }

    public CandidatureDTO toDto(Candidature candidature) {
        return new CandidatureDTO(
                candidature.getId(),
                candidature.getCandidatId(),
                candidature.getSujetId(),
                candidature.getSujet() == null ? null : candidature.getSujet().getTitre(),
                candidature.getPathFile()
        );
    }

    public List<CandidatureDTO> toCandidatureDtos(List<Candidature> candidatures) {
        return candidatures.stream().map(this::toDto).collect(toList());
    }

    public SujetDTO toDto(Sujet sujet) {
        return new SujetDTO(
                sujet.getId(),
                sujet.getTitre(),
                sujet.getDescription(),
                sujet.isPublier(),
                sujet.getProfesseurId(),
                sujet.getCoDirecteurId(),
                sujet.getFormationDoctoraleId()
        );
    }

    public Sujet toSujet(SujetCreationDTO dto, Long professeurId) {
        return new Sujet(
                dto.getTitre(),
                dto.getDescription(),
                false,
                professeurId,
                dto.getCoDirecteurId(),
                dto.getFormationDoctoraleId()
        );
    }
}
