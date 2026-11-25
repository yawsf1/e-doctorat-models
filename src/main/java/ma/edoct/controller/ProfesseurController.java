package ma.edoct.controller;

import jakarta.validation.Valid;
import ma.edoct.dto.CandidatDTO;
import ma.edoct.dto.SujetCreationDTO;
import ma.edoct.dto.SujetDTO;
import ma.edoct.service.ProfesseurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professeurs")
public class ProfesseurController {
    private final ProfesseurService professeurService;

    public ProfesseurController(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    @PostMapping("/{id}/sujets")
    public SujetDTO proposerSujet(@PathVariable Long id, @Valid @RequestBody SujetCreationDTO dto) {
        return professeurService.proposerSujet(id, dto);
    }

    @PutMapping("/{id}/sujets/{sujetId}")
    public Optional<SujetDTO> modifierSujet(@PathVariable Long id,
                                            @PathVariable Long sujetId,
                                            @Valid @RequestBody SujetCreationDTO dto) {
        return professeurService.modifierSujet(id, sujetId, dto);
    }

    @GetMapping("/{id}/candidats")
    public List<CandidatDTO> consulterCandidats(@PathVariable Long id) {
        return professeurService.consulterCandidats(id);
    }
}
