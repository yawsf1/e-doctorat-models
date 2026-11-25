package ma.edoct.dto;

public class CandidatureDTO {
    private Long id;
    private Long candidatId;
    private Long sujetId;
    private String titreSujet;
    private String pathFile;

    public CandidatureDTO() {}

    public CandidatureDTO(Long id, Long candidatId, Long sujetId, String titreSujet, String pathFile) {
        this.id = id;
        this.candidatId = candidatId;
        this.sujetId = sujetId;
        this.titreSujet = titreSujet;
        this.pathFile = pathFile;
    }

    public Long getId() { return id; }
    public Long getCandidatId() { return candidatId; }
    public Long getSujetId() { return sujetId; }
    public String getTitreSujet() { return titreSujet; }
    public String getPathFile() { return pathFile; }
}
