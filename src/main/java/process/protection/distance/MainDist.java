package process.protection.distance;

import iec61850.nodes.Breaker.CSWI;
import iec61850.nodes.Breaker.XCBR;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.NHMIP;
import iec61850.nodes.gui.other.NHMIPoint;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.protection.*;
import iec61850.nodes.protection.protectionrelatedfunctions.RDIR;
import iec61850.nodes.protection.protectionrelatedfunctions.RPSB;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.DEL;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.posledovatelnosti.MSQI;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;

import java.util.ArrayList;
import java.util.List;

public class MainDist {

    public static void main(String[] args) {

        List<NHMIPoint<Double, Double>> pointsList = new ArrayList<>();
        List<NHMIPoint<Double, Double>> pointsListN = new ArrayList<>();

        RPSB rpsb1 = new RPSB();
        RPSB rpsb2 = new RPSB();
        RPSB rpsb3 = new RPSB();

        DEL Z = new DEL();
        LSVC lsvc = new LSVC();
        NHMI nhmi = new NHMI();
        NHMIP nhmip = new NHMIP();
        NHMIP nhmipN = new NHMIP();
        MMXU mmxu = new MMXU();
        PDIS pdis1 = new PDIS();
        PDIS pdis2 = new PDIS();
        PDIS pdis3 = new PDIS();
        PDIS pdis1N = new PDIS();
        PDIS pdis2N = new PDIS();
        
        
        
        XCBR XCBR = new XCBR();
        CSWI CSWI = new CSWI();
        MSQI MSQI = new MSQI();

        PTRC PTRC = new PTRC();

        lsvc.readComtrade("E:\\Idea\\Алгоритмы\\Алгоритмы ЛР№2\\ЛР2\\Опыты\\KZ1");

        mmxu.setInstIa(lsvc.getSignals().get(5));
        mmxu.setInstIb(lsvc.getSignals().get(4));
        mmxu.setInstIc(lsvc.getSignals().get(3));
        mmxu.setInstUa((lsvc.getSignals().get(0)));
        mmxu.setInstUb(lsvc.getSignals().get(1));
        mmxu.setInstUc(lsvc.getSignals().get(2));

        MSQI.setImbA(mmxu.getA());
        MSQI.setImbV(mmxu.getPhV());

        rpsb1.getSwgVal().getSetMag().getF().setValue(0.001f);

        rpsb2.getSwgVal().getSetMag().getF().setValue(0.001f);

        rpsb3.getSwgVal().getSetMag().getF().setValue(0.001f);


        boolean OpKachaniya = rpsb1.getOp().getGeneral().getValue() || rpsb2.getOp().getGeneral().getValue() || rpsb3.getOp().getGeneral().getValue(); //True если КЗ
        Z.getPhsAB().setCVal(mmxu.getZab());
        Z.getPhsBC().setCVal(mmxu.getZbc());
        Z.getPhsCA().setCVal(mmxu.getZca());

        double x0 = 0;
        double y0 = 0;
        double r = 200;
        for(double x= -2*r; x<= 2*r; x += 0.1) {
            double y = Math.sqrt(Math.pow(r, 2) - Math.pow((x-x0), 2)) + y0;
            pointsList.add(new NHMIPoint<>(x, y));
            pointsList.add(new NHMIPoint<>(x, -y));
        }

        double xN0 = 155;
        double yN0 = 0;
        double rN = 200;
        for(double xN= -2*r; xN<= 2*r; xN += 0.1) {
            double yN = Math.sqrt(Math.pow(rN, 2) - Math.pow((xN-xN0), 2)) + yN0;
            pointsListN.add(new NHMIPoint<>(xN, yN+150));
            pointsListN.add(new NHMIPoint<>(xN, -yN+150));
        }



        pdis1.setOpKachaniya(OpKachaniya);
        pdis1.setZ(mmxu.getZf());
        pdis1.getR0().getSetMag().getF().setValue(0f);
        pdis1.getX0().getSetMag().getF().setValue(0f);
        pdis1.getZust().getSetMag().getF().setValue(200f);
        pdis1.getOpDITmms().setSetVal(0);

        pdis2.setOpKachaniya(OpKachaniya);
        pdis2.setZ(mmxu.getZf());
        pdis2.getR0().getSetMag().getF().setValue(0f);
        pdis2.getX0().getSetMag().getF().setValue(0f);
        pdis2.getZust().getSetMag().getF().setValue(200f);
        pdis2.getOpDITmms().setSetVal(250);

        pdis3.setOpKachaniya(OpKachaniya);
        pdis3.setZ(mmxu.getZf());
        pdis3.getR0().getSetMag().getF().setValue(0f);
        pdis3.getX0().getSetMag().getF().setValue(0f);
        pdis3.getZust().getSetMag().getF().setValue(200f);
        pdis3.getOpDITmms().setSetVal(500);

        pdis1N.setOpKachaniya(OpKachaniya);
        pdis1N.setZ(mmxu.getZf());
        pdis1N.getR0().getSetMag().getF().setValue(155f);
        pdis1N.getX0().getSetMag().getF().setValue(150f);
        pdis1N.getZust().getSetMag().getF().setValue(200f);
        pdis1N.getOpDITmms().setSetVal(0);

        pdis2N.setOpKachaniya(OpKachaniya);
        pdis2N.setZ(mmxu.getZf());
        pdis2N.getR0().getSetMag().getF().setValue(155f);
        pdis2N.getX0().getSetMag().getF().setValue(150f);
        pdis2N.getZust().getSetMag().getF().setValue(200f);
        pdis2N.getOpDITmms().setSetVal(150);



        CSWI.getPos().getCtlVal().setValue(true);
        CSWI.getPos().getStVal().setValue((byte) 2);

        PTRC.getOp().add(pdis1.getOp());
        PTRC.getOp().add(pdis2.getOp());
        PTRC.getOp().add(pdis3.getOp());
        PTRC.getOp().add(pdis1N.getOp());
        PTRC.getOp().add(pdis2N.getOp());

        CSWI.setOpOpn(PTRC.getTr());

        CSWI.setOpCls(PTRC.getTr());


        XCBR.setPos(CSWI.getPos());


//        nhmi.addSignals(
//                new NHMISignal("Ua", lsvc.getSignals().get(0).getInstMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Ub", lsvc.getSignals().get(1).getInstMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Uc", lsvc.getSignals().get(2).getInstMag().getF())
//        );
        nhmi.addSignals(
                new NHMISignal("ток А", lsvc.getSignals().get(5).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("ток Б", lsvc.getSignals().get(4).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("ток С", lsvc.getSignals().get(3).getInstMag().getF())
        );


        nhmi.addSignals(
                new NHMISignal("Z АB", mmxu.getZab().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal(" Срабатывание:", rpsb1.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal(" Срабатывание:", rpsb2.getOp().getGeneral())
        );



        nhmi.addSignals(
                new NHMISignal("Z БC", mmxu.getZbc().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Z СA", mmxu.getZca().getMag().getF())
        );

        nhmip.drawCharacteristic("Characteristic", pointsList);
        nhmip.addSignals(
                new NHMISignal("Zab", mmxu.getZab().getX().getF(), mmxu.getZab().getY().getF()),
        new NHMISignal("Zbc", mmxu.getZbc().getX().getF(), mmxu.getZbc().getY().getF()),
        new NHMISignal("Zca", mmxu.getZca().getX().getF(), mmxu.getZca().getY().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Breaker", XCBR.getPos().getStVal())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PDIS1", pdis1.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PDIS2", pdis2.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PDIS3", pdis3.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PDIS1N", pdis1N.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PDIS2N", pdis2N.getOp().getGeneral())
        );
        nhmipN.drawCharacteristic("Characteristic", pointsListN);
        nhmipN.addSignals(
                new NHMISignal("Zab", mmxu.getZab().getX().getF(), mmxu.getZab().getY().getF()),
                new NHMISignal("Zbc", mmxu.getZbc().getX().getF(), mmxu.getZbc().getY().getF()),
                new NHMISignal("Zca", mmxu.getZca().getX().getF(), mmxu.getZca().getY().getF())
        );





        while(lsvc.hasNext()) {
            rpsb1.process(mmxu.getZab());
            rpsb2.process(mmxu.getZbc());
            rpsb3.process(mmxu.getZca());
            lsvc.process();
            nhmi.process();
            nhmip.process();
            nhmipN.process();
            mmxu.process();
            pdis1.process();
            pdis2.process();
            pdis3.process();
            pdis1N.process();
            pdis2N.process();
            CSWI.process();
            XCBR.process();
            MSQI.process();

            PTRC.process();
        }
    }
}
