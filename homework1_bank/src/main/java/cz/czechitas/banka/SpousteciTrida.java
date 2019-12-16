package cz.czechitas.banka;

public class SpousteciTrida {

    public static void main(String[] args) {
        System.out.println("Založení běžného účtu:");
        BeznyUcet ucet = new BeznyUcet(500, 5000);
        System.out.println("Zůstatek běžného účtu = " + ucet.getZustatek());
        System.out.println("Limit přečerpání běžného účtu = " + ucet.getLimitPrecerpani());
        ucet.vyberPenize(10000);
        System.out.println("Test účtu s chybou:");
        BeznyUcet ucet1 = new BeznyUcet(-500, 7000);
        System.out.println("Založení spořícího účtu:");
        SporiciUcet sporiciUcet1 = new SporiciUcet(300, 0.005);
        System.out.println("Zůstatek spořícího účtu = " + ucet.getZustatek());
        sporiciUcet1.vypocitejRocniUrokAVlozHoNaUcet();
        System.out.println("Zůstatek spořícího účtu po připočtení úroku = " + sporiciUcet1.getZustatek());


    }

}
