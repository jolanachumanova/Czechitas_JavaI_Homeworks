package cz.czechitas.banka;

public class SporiciUcet {

    private double zustatek;
    private double urokovaMira;

    public SporiciUcet(double pocatecniCastka) {
        if (pocatecniCastka >= 0) {
        zustatek = pocatecniCastka;
        } else {
            System.out.println("Chybna pocatecni castka spořícího účtu!");
            return;
        }
    }

    public SporiciUcet(double pocatecniCastka, double pocatecniUrokovaMira) {
        if (pocatecniCastka >= 0 && pocatecniUrokovaMira >= 0) {
        zustatek = pocatecniCastka;
        urokovaMira = pocatecniUrokovaMira;
        } else {
            System.out.println("Chybny pocatecni udaj!");
            return;
        }
    }

    public double getZustatek() {
        return zustatek;
    }

    public double getUrokovaMira() {
        return urokovaMira;
    }
    public boolean vyberPenize(double castka) {
        if (castka >= 0) {
            if ((zustatek) >= castka) {

                zustatek = zustatek - castka;

                return true;
            } else {
                System.out.println("Na uctu neni dostatek penez pro vyber!");
                return false;
            }
        } else {
            System.out.println("Chybna hodnota vybirane castky!");
            return false;
        }
    }
    public boolean vlozPenize(double castka) {
        if (castka >= 0) {
            zustatek = zustatek + castka;
            return true;
        } else {
            System.out.println("Chybna hodnota vlozene castky!");
            return false;
        }
    }
    public void vypocitejRocniUrokAVlozHoNaUcet(){
        double rocniUrok = zustatek*urokovaMira;
        vlozPenize(rocniUrok);
    }
}
