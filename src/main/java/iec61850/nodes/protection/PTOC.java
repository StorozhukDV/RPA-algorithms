package iec61850.nodes.protection;

import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.WYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.*;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PTOC extends LN {

    private WYE A = new WYE();
    private ACT Op = new ACT();
    private ACD Str = new ACD();
    private ASG StrVal = new ASG();
    private ING OpDITmms = new ING();
    private ENG DirMod = new ENG();
    private ACD Dir = new ACD();

    private double CntA = 0;
    private double CntB = 0;
    private double CntC = 0;

    @Override
    public void process() {

        boolean phsA = A.getPhsA().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();
        boolean phsB = A.getPhsB().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();
        boolean phsC = A.getPhsC().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();
        boolean neut = A.getPhsNeut().getCVal().getMag().getF().getValue() > 1000;

        boolean general = phsA || phsB || phsC || neut;
        if (phsA) {
            CntA += 1;
        }
        else{
            CntA =0;
        }
        if (phsB) {
            CntB += 1;
        }
        else{
            CntB =0;
        }
        if (phsC) {
            CntC += 1;
        }
        else{
            CntC =0;
        }

        if (DirMod.getStVal().getValue() == Direction.FORWARD){
            if (Dir.getDirPhsA().getValue() == Direction.BACKWARD){
                CntA = 0;
            }
        }
        if (DirMod.getStVal().getValue() == Direction.FORWARD){
            if (Dir.getDirPhsB().getValue() == Direction.BACKWARD){
                CntB = 0;
            }
        }
        if (DirMod.getStVal().getValue() == Direction.FORWARD){
            if (Dir.getDirPhsB().getValue() == Direction.BACKWARD){
                CntC = 0;
            }
        }
        Op.getGeneral().setValue(CntA > OpDITmms.getSetVal()
                || CntB > OpDITmms.getSetVal()
                || CntC > OpDITmms.getSetVal() );
        Op.getPhsA().setValue(CntA > OpDITmms.getSetVal());
        Op.getPhsB().setValue(CntB > OpDITmms.getSetVal());
        Op.getPhsC().setValue(CntC > OpDITmms.getSetVal());
        Op.getNeut().setValue(neut);
    }
}
