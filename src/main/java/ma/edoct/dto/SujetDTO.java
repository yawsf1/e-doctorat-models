package ma.edoct.dto;

public class SujetDTO {
    private Long id;
    private String titre;
    private String description;
    private boolean publier;
    private Long professeurId;
    private Long coDirecteurId;
    private Long formationDoctoraleId;

    public SujetDTO() {}

    public SujetDTO(Long id, String titre, String description, boolean publier,
                    Long professeurId, Long coDirecteurId, Long formationDoctoraleId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.publier = publier;
        this.professeurId = professeurId;
        this.coDirecteurId = coDirecteurId;
        this.formationDoctoraleId = formationDoctoraleId;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public boolean isPublier() { return publier; }
    public Long getProfesseurId() { return professeurId; }
    public Long getCoDirecteurId() { return coDirecteurId; }
    public Long getFormationDoctoraleId() { return formationDoctoraleId; }
}
