package iec61850.nodes.protection;

import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.DEL;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.WYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.*;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PDIS extends LN {

    private DEL Z = new DEL();
    private ACT Op = new ACT();

    private boolean OpKachaniya;

    private ASG x0 = new ASG();
    private ASG r0 = new ASG();
    private ASG Zust = new ASG();

    private ING OpDITmms = new ING();

    private double CntAB = 0;
    private double CntBC = 0;
    private double CntCA = 0;

    @Override
    public void process() {

        boolean phsAB = Math.pow(Z.getPhsAB().getCVal().getX().getF().getValue() - r0.getSetMag().getF().getValue(),2) +
                Math.pow(Z.getPhsAB().getCVal().getY().getF().getValue() - x0.getSetMag().getF().getValue(),2) <= Math.pow((Zust.getSetMag().getF().getValue()),2);

        boolean phsBC = Math.pow(Z.getPhsBC().getCVal().getX().getF().getValue() - r0.getSetMag().getF().getValue(),2) +
                Math.pow(Z.getPhsBC().getCVal().getY().getF().getValue() - x0.getSetMag().getF().getValue(),2) <= Math.pow((Zust.getSetMag().getF().getValue()),2);

        boolean phsCA = Math.pow(Z.getPhsCA().getCVal().getX().getF().getValue() - r0.getSetMag().getF().getValue(),2) +
                Math.pow(Z.getPhsCA().getCVal().getY().getF().getValue() - x0.getSetMag().getF().getValue(),2) <= Math.pow((Zust.getSetMag().getF().getValue()),2);



        if (phsAB) {
            CntAB += 1;
        }
        else{
            CntAB =0;
        }
        if (phsBC) {
            CntBC += 1;
        }
        else{
            CntBC =0;
        }
        if (phsCA) {
            CntCA += 1;
        }
        else{
            CntCA =0;
        }


        if (OpKachaniya){
            CntAB = 0;
            CntBC = 0;
            CntCA = 0;
        }

        Op.getGeneral().setValue(CntAB > OpDITmms.getSetVal()
                || CntBC > OpDITmms.getSetVal()
                || CntCA > OpDITmms.getSetVal() );
        Op.getPhsA().setValue(CntAB > OpDITmms.getSetVal());
        Op.getPhsB().setValue(CntBC > OpDITmms.getSetVal());
        Op.getPhsC().setValue(CntCA > OpDITmms.getSetVal());
    }
}
