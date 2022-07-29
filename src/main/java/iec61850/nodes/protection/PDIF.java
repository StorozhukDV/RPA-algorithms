package iec61850.nodes.protection;

import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.CSD;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.HWYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.WYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.*;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class PDIF extends LN {

    private ArrayList<HWYE> inputHarm = new ArrayList<>();  //Гармонники посчитанные в HWYE

    private WYE DifAClc = new WYE();                        //диф ток посчитанный в rmxu
    private WYE TripPoint = new WYE();

    private ACT Op = new ACT();                             //срабатывание


    private ASG StrVal = new ASG();                         //Уставка по диф.току
    private ASG StrHarm = new ASG();                        //Уставка по гармонике
    private ING MinOpTmms = new ING();                      //Уставка по времени

    private CSD TmASt = new CSD();                          //Тормозная характеристика

    private double CntA = 0;
    private double CntB = 0;
    private double CntC = 0;

    private Attribute<Boolean> BlockA = new Attribute<>(false);
    private Attribute<Boolean> BlockB = new Attribute<>(false);
    private Attribute<Boolean> BlockC = new Attribute<>(false);


    @Override
    public void process() {
        for (HWYE w:inputHarm){
            if(!BlockA.getValue()){
                BlockA.setValue(w.getPhsAHar().get(2).getMag().getF().getValue()/w.getPhsAHar().get(1).getMag().getF().getValue()
                        > StrHarm.getSetMag().getF().getValue());
            }
            else{
                BlockA.setValue(false);
            }
            if(!BlockB.getValue()){
                BlockB.setValue(w.getPhsBHar().get(2).getMag().getF().getValue()/w.getPhsBHar().get(1).getMag().getF().getValue()
                        > StrHarm.getSetMag().getF().getValue());
            }
            else{
                BlockB.setValue(false);
            }
            if(!BlockC.getValue()){
                BlockC.setValue(w.getPhsCHar().get(2).getMag().getF().getValue()/w.getPhsCHar().get(1).getMag().getF().getValue()
                        > StrHarm.getSetMag().getF().getValue());
            }
            else{
                BlockC.setValue(false);
            }
        }

        boolean phsA = DifAClc.getPhsA().getCVal().getMag().getF().getValue() > TripPoint.getPhsA().getCVal().getMag().getF().getValue();
        boolean phsB = DifAClc.getPhsB().getCVal().getMag().getF().getValue() > TripPoint.getPhsB().getCVal().getMag().getF().getValue();
        boolean phsC = DifAClc.getPhsC().getCVal().getMag().getF().getValue() > TripPoint.getPhsC().getCVal().getMag().getF().getValue();


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

        if(BlockA.getValue()||BlockB.getValue()||BlockC.getValue()) CntA = 0;
        if(BlockA.getValue()||BlockB.getValue()||BlockC.getValue()) CntB = 0;
        if(BlockA.getValue()||BlockB.getValue()||BlockC.getValue()) CntC = 0;




        Op.getGeneral().setValue(CntA > MinOpTmms.getSetVal()
                || CntB > MinOpTmms.getSetVal()
                || CntC > MinOpTmms.getSetVal() );
        Op.getPhsA().setValue(CntA > MinOpTmms.getSetVal());
        Op.getPhsB().setValue(CntB > MinOpTmms.getSetVal());
        Op.getPhsC().setValue(CntC > MinOpTmms.getSetVal());

    }
}
