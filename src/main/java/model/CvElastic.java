package model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class CvElastic {
    private String id;
    private String make;
    private String model;
    private String makemodel;
    private String make_id;
    private String model_id;
    private String vertical = "CV";
    private String cvVehicleClass = "MISCD";
    private List<String> supportedFuel = List.of("Diesel");
    private List<String> supportedSC;
    private List<String> supportedCC;
    private List<Integer> supportedGVW;
    private List<CvType> cv_type;
}
