package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class CvType {
    private Long id = 62L;
    private String vehicle_type = "Default Misc ICICI";
    private String body_type;
    private String usage_type;
    private String subtype_payout = "DEFAULT MISC ICICIC";
    private String display_name = "DEFAULT MISC ICICI";
}
