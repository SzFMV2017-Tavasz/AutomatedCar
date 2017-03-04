package hu.oe.nik.szfmv17t.automatedcar.bus;

/**
 * Created by SebestyenMiklos on 2017. 03. 04..
 */
public enum SignalTypeEnum {
    SMI_BreakPedal(1),
    SMI_Gaspedal(11),
    SMI_Gear(12),
    Modelling(2),
    Physics(3),
    Physics_Speed(31),
    Physics_Gear(32),
    Visualisation(4);

    int numVal;

    SignalTypeEnum(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

}
