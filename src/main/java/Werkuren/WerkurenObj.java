package Werkuren;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class WerkurenObj {

    private int timeSpendInMinutes = 0;
    private String  WERKNEMER;
    private String   WERKBONNUMMER;
    private String   DATUM;
    private String   BEGINTIJDH;
    private String   BEGINTIJDM60;
    private String   EINDTIJDH;
    private String    EINDTIJDM60;
}
