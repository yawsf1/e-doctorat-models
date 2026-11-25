package ma.edoct.controller;

import ma.edoct.dto.CandidatureDTO;
import ma.edoct.dto.CandidatDTO;
import ma.edoct.service.CandidatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidats")
public class CandidatController {
    private final CandidatService candidatService;

    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;
    }

    // Récupération avec @RequestParam : /candidats?id=1
    @GetMapping
    public Optional<CandidatDTO> getCandidatParRequestParam(@RequestParam Long id) {
        return candidatService.findById(id);
    }

    // Récupération avec @PathVariable : /candidats/1
    @GetMapping("/{id}")
    public Optional<CandidatDTO> getCandidatParPathVariable(@PathVariable Long id) {
        return candidatService.findById(id);
    }

    // Candidater sur 3 sujets maximum, avec document de projet de recherche optionnel.
    @PostMapping("/{id}/candidatures")
    public List<CandidatureDTO> candidater(@PathVariable Long id,
                                           @RequestBody List<Long> sujetIds,
                                           @RequestParam(name = "pathFile", required = false) String pathFile) {
        return candidatService.candidater(id, sujetIds, pathFile);
    }

    // Modification possible avant la date de fermeture du calendrier de candidature.
    @PutMapping("/{id}/candidatures")
    public List<CandidatureDTO> modifierCandidatures(@PathVariable Long id,
                                                     @RequestBody List<Long> sujetIds,
                                                     @RequestParam(name = "pathFile", required = false) String pathFile) {
        return candidatService.modifierCandidatures(id, sujetIds, pathFile);
    }
}
