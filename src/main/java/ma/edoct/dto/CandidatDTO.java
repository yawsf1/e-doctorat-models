package ma.edoct.dto;

public class CandidatDTO {
    private Long id;
    private String cne;
    private String cin;
    private String nomCandidatAr;
    private String prenomCandidatAr;
    private String ville;
    private String telCandidat;
    private String pathCv;
    private String pathPhoto;

    public CandidatDTO() {}

    public CandidatDTO(Long id, String cne, String cin, String nomCandidatAr, String prenomCandidatAr,
                       String ville, String telCandidat, String pathCv, String pathPhoto) {
        this.id = id;
        this.cne = cne;
        this.cin = cin;
        this.nomCandidatAr = nomCandidatAr;
        this.prenomCandidatAr = prenomCandidatAr;
        this.ville = ville;
        this.telCandidat = telCandidat;
        this.pathCv = pathCv;
        this.pathPhoto = pathPhoto;
    }

    public Long getId() { return id; }
    public String getCne() { return cne; }
    public String getCin() { return cin; }
    public String getNomCandidatAr() { return nomCandidatAr; }
    public String getPrenomCandidatAr() { return prenomCandidatAr; }
    public String getVille() { return ville; }
    public String getTelCandidat() { return telCandidat; }
    public String getPathCv() { return pathCv; }
    public String getPathPhoto() { return pathPhoto; }
}
