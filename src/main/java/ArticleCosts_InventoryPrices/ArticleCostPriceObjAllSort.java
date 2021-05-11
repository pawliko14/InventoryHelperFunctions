package ArticleCosts_InventoryPrices;

public class ArticleCostPriceObjAllSort {

    String Artykul;
    String CenaSredniaWazona;
    String CenaPracy;
    String SumaObu;
    String Soort;


    @Override
    public String toString() {
        return "ArticleCostPriceObjAllSort{" +
                "Artykul='" + Artykul + '\'' +
                ", CenaSredniaWazona='" + CenaSredniaWazona + '\'' +
                ", CenaPracy='" + CenaPracy + '\'' +
                ", SumaObu='" + SumaObu + '\'' +
                ", Soort='" + Soort + '\'' +
                '}';
    }

    public String getArtykul() {
        return Artykul;
    }

    public void setArtykul(String artykul) {
        Artykul = artykul;
    }

    public String getCenaSredniaWazona() {
        return CenaSredniaWazona;
    }

    public void setCenaSredniaWazona(String cenaSredniaWazona) {
        CenaSredniaWazona = cenaSredniaWazona;
    }

    public String getCenaPracy() {
        return CenaPracy;
    }

    public void setCenaPracy(String cenaPracy) {
        CenaPracy = cenaPracy;
    }

    public String getSumaObu() {
        return SumaObu;
    }

    public void setSumaObu(String sumaObu) {
        SumaObu = sumaObu;
    }

    public String getSoort() {
        return Soort;
    }

    public void setSoort(String soort) {
        Soort = soort;
    }

    public ArticleCostPriceObjAllSort(String artykul, String cenaSredniaWazona, String cenaPracy, String sumaObu, String soort) {
        Artykul = artykul;
        CenaSredniaWazona = cenaSredniaWazona;
        CenaPracy = cenaPracy;
        SumaObu = sumaObu;
        Soort = soort;
    }
}
