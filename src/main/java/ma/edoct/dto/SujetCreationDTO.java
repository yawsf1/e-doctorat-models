package ma.edoct.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SujetCreationDTO {
    @NotBlank @Size(max = 255) private String titre;
    private String description;
    @NotNull private Long formationDoctoraleId;
    private Long coDirecteurId;

    public SujetCreationDTO() {}

    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public Long getFormationDoctoraleId() { return formationDoctoraleId; }
    public Long getCoDirecteurId() { return coDirecteurId; }

    public void setTitre(String value) { titre = value; }
    public void setDescription(String value) { description = value; }
    public void setFormationDoctoraleId(Long value) { formationDoctoraleId = value; }
    public void setCoDirecteurId(Long value) { coDirecteurId = value; }
}
