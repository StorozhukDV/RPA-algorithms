package process.protection.current;

import iec61850.nodes.Breaker.CSWI;
import iec61850.nodes.Breaker.XCBR;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.protection.PTOC;

/**
 * @author Александр Холодов
 * @created 03.2022
 * @project Programming2022
 * @description
 */
public class Main {


    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        NHMI nhmi = new NHMI();
        MMXU mmxu = new MMXU();
        PTOC PTOC1d = new PTOC();
        PTOC PTOC2d = new PTOC();
        XCBR XCBR = new XCBR();
        CSWI CSWI = new CSWI();



        lsvc.readComtrade("E:\\Idea\\Опыты\\Начало линии\\PhB20");

        mmxu.setInstIa(lsvc.getSignals().get(0));
        mmxu.setInstIb(lsvc.getSignals().get(1));
        mmxu.setInstIc(lsvc.getSignals().get(2));



        PTOC1d.setA(mmxu.getA());
        PTOC1d.getStrVal().getSetMag().getF().setValue(1000F);

        PTOC2d.setA(mmxu.getA());
        PTOC2d.getStrVal().getSetMag().getF().setValue(750F);
        PTOC2d.getOpDITmms().setSetVal(250);


        CSWI.getPos().getCtlVal().setValue(true);
        CSWI.getPos().getStVal().setValue((byte) 2);



        CSWI.setOpOpn(PTOC1d.getOp());
        XCBR.setPos(CSWI.getPos());





        nhmi.addSignals(
                new NHMISignal("TestSignal1", lsvc.getSignals().get(0).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal2", lsvc.getSignals().get(1).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal3", lsvc.getSignals().get(2).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal 1RMS", mmxu.getA().getPhsA().getCVal().getMag().getF()),
                new NHMISignal("Ustavka1St", PTOC1d.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", PTOC2d.getStrVal().getSetMag().getF()));
        nhmi.addSignals(
                new NHMISignal("TestSignal 2RMS", mmxu.getA().getPhsB().getCVal().getMag().getF()),
                new NHMISignal("Ustavka1St", PTOC1d.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", PTOC2d.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal 3RMS", mmxu.getA().getPhsC().getCVal().getMag().getF()),
                new NHMISignal("Ustavka1St", PTOC1d.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", PTOC2d.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal1", PTOC1d.getOp().getPhsA())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal2", PTOC2d.getOp().getPhsA())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal1", PTOC1d.getOp().getPhsB())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal2", PTOC2d.getOp().getPhsB())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal1", PTOC1d.getOp().getPhsC())
        );


        nhmi.addSignals(
                new NHMISignal("TestSignal2", PTOC2d.getOp().getPhsC())
        );
        nhmi.addSignals(
                new NHMISignal("Breaker", XCBR.getPos().getStVal())
        );



        while(lsvc.hasNext()) {
            lsvc.process();
            nhmi.process();
            mmxu.process();
            PTOC1d.process();
            PTOC2d.process();
            CSWI.process();
            XCBR.process();


//            System.out.println(lsvc.getSignals().get(0).getInstMag().getF().getValue() + "А");
//            System.out.println(lsvc.getSignals().get(1).getInstMag().getF().getValue() + "В");
//            System.out.println(lsvc.getSignals().get(2).getInstMag().getF().getValue() + "С");

        }

    }



}
