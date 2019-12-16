package cz.czechitas.banka;

import sun.management.*;

public class BeznyUcet {

    private double zustatek;
    private double limitPrecerpani;

    public BeznyUcet(double pocatecniCastka) {
        if (pocatecniCastka >= 0) {
            zustatek = pocatecniCastka;
        } else {
            System.out.println("Chybna pocatecni castka běžného účtu!");
            return;
        }
    }

    public BeznyUcet(double pocatecniCastka, double pocatecniLimitPrecerpani) {
        if (pocatecniCastka >= 0 && pocatecniLimitPrecerpani >= 0) {
            zustatek = pocatecniCastka;
            limitPrecerpani = pocatecniLimitPrecerpani;
        } else {
            System.out.println("Chybny pocatecni udaj!");
            return;
        }
    }

    public double getZustatek() {
        return zustatek;
    }

    public double getLimitPrecerpani() {
        return limitPrecerpani;
    }

    public double getPouzitelnyZustatek() {
        double pouzitelnyZustatek = zustatek + limitPrecerpani;
        return pouzitelnyZustatek;
    }

    public boolean vyberPenize(double castka) {
        if (castka >= 0) {
            if ((zustatek + limitPrecerpani) >= castka) {

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
}
