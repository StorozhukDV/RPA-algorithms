package iec61850.nodes.protection.protectionrelatedfunctions;


import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.CSD;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.HWYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.Vector;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.WYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.SAV;
import lombok.Data;

import java.util.ArrayList;


@Data
public class RMXU extends LN {
    private ArrayList<WYE> inputA = new ArrayList<>();
    private WYE dif = new WYE();
    private ArrayList<WYE> Difoutput = new ArrayList<>();


    private Vector rstCurrent = new Vector();
    private CSD TmASt = new CSD();
    private WYE RstA = new WYE();
    private WYE TripPoint = new WYE();          //Тормозная характеристика

    private Vector Idifa = new Vector();
    private Vector Idifb = new Vector();
    private Vector Idifc = new Vector();

    private float dif0;
    private float k;
    private float rst0;


    double St = 500*(Math.pow(10,3));
    ArrayList<Float> U = new ArrayList<>();
    ArrayList<Float> Kt = new ArrayList<>();
    ArrayList<Float> basis = new ArrayList<>();


    @Override
    public void process() {
        rstCurrent.ToVector(0,0);

        U.add(500F);
        U.add(220F);
        U.add(10F);

        Kt.add(160F);
        Kt.add(400F);
        Kt.add(8000F);

        for (int i=0;i!=2;i++){
            basis.add((float) (St / (U.get(i)* Kt.get(i)*Math.sqrt(3))));
            inputA.get(i).getPhsA().getCVal().getMag().getF().setValue(inputA.get(i).getPhsA().getCVal().getMag().getF().getValue()/basis.get(i));
            inputA.get(i).getPhsB().getCVal().getMag().getF().setValue(inputA.get(i).getPhsB().getCVal().getMag().getF().getValue()/basis.get(i));
            inputA.get(i).getPhsC().getCVal().getMag().getF().setValue(inputA.get(i).getPhsC().getCVal().getMag().getF().getValue()/basis.get(i));
        }



        Idifa.Slozhenie(inputA.get(0).getPhsA().getCVal(), inputA.get(1).getPhsA().getCVal(), inputA.get(2).getPhsA().getCVal());
        Idifa.ToVector(Idifa.getX().getF().getValue(),Idifa.getY().getF().getValue());

        Idifb.Slozhenie(inputA.get(0).getPhsB().getCVal(), inputA.get(1).getPhsB().getCVal(), inputA.get(2).getPhsB().getCVal());
        Idifb.ToVector(Idifb.getX().getF().getValue(),Idifb.getY().getF().getValue());

        Idifc.Slozhenie(inputA.get(0).getPhsC().getCVal(), inputA.get(1).getPhsC().getCVal(),inputA.get(2).getPhsC().getCVal());
        Idifc.ToVector(Idifc.getX().getF().getValue(),Idifc.getY().getF().getValue());

        dif.getPhsA().setCVal(Idifa);
        dif.getPhsB().setCVal(Idifb);
        dif.getPhsC().setCVal(Idifc);

        Difoutput.add(dif);

        dif0 = TmASt.getCrvPvs().get(1).getYVal().getValue();
        rst0 = TmASt.getCrvPvs().get(1).getXVal().getValue();
        k = (TmASt.getCrvPvs().get(2).getYVal().getValue() - TmASt.getCrvPvs().get(1).getYVal().getValue())/
                (TmASt.getCrvPvs().get(2).getXVal().getValue() - TmASt.getCrvPvs().get(1).getXVal().getValue());

        for (WYE w:inputA){
            if(w.getPhsA().getCVal().getMag().getF().getValue() > rstCurrent.getMag().getF().getValue()){
                rstCurrent.Artog(w.getPhsA().getCVal().getX().getF().getValue(),w.getPhsA().getCVal().getY().getF().getValue());
            }
            if(w.getPhsB().getCVal().getMag().getF().getValue() > rstCurrent.getMag().getF().getValue()){
                rstCurrent.Artog(w.getPhsB().getCVal().getX().getF().getValue(),w.getPhsB().getCVal().getY().getF().getValue());
            }
            if(w.getPhsC().getCVal().getMag().getF().getValue() > rstCurrent.getMag().getF().getValue()){
                rstCurrent.Artog(w.getPhsC().getCVal().getX().getF().getValue(),w.getPhsC().getCVal().getY().getF().getValue());
            }

        }
        RstA.getPhsA().getCVal().Artog(rstCurrent.getX().getF().getValue(),rstCurrent.getY().getF().getValue());
        RstA.getPhsB().getCVal().Artog(rstCurrent.getX().getF().getValue(),rstCurrent.getY().getF().getValue());
        RstA.getPhsC().getCVal().Artog(rstCurrent.getX().getF().getValue(),rstCurrent.getY().getF().getValue());

        if (rstCurrent.getMag().getF().getValue() < rst0) {
            TripPoint.getPhsA().getCVal().ToVector(dif0,0);
            TripPoint.getPhsB().getCVal().ToVector(dif0,0);
            TripPoint.getPhsC().getCVal().ToVector(dif0,0);
        }
        else{
            TripPoint.getPhsA().getCVal().ToVector(rstCurrent.getMag().getF().getValue()*k,0);
            TripPoint.getPhsB().getCVal().ToVector(rstCurrent.getMag().getF().getValue()*k,0);
            TripPoint.getPhsC().getCVal().ToVector(rstCurrent.getMag().getF().getValue()*k,0);
        }
    }
}
