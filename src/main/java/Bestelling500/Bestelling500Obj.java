package Bestelling500;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Bestelling500Obj {

    String leverancier;
    String Ordernummer;
    String artikelcode;
    String artikelomschrijving;
    String textofExistance; // "500 not exists in hacosoft"
    String besteld;
    String gelerved;

}
