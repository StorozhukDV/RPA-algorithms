package process.protection.DZT;

import iec61850.nodes.Breaker.CSWI;
import iec61850.nodes.Breaker.XCBR;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MHAI;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.protection.*;
import iec61850.nodes.protection.protectionrelatedfunctions.RDIR;
import iec61850.nodes.protection.protectionrelatedfunctions.RMXU;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.posledovatelnosti.MSQI;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.Point;

public class MainDZT {

    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        NHMI nhmiA = new NHMI();
        NHMI nhmiB = new NHMI();
        NHMI nhmiC = new NHMI();
        NHMI nhmiDif = new NHMI();

        RMXU rmxu = new RMXU();
        PDIF pdif = new PDIF();

        MMXU mmxu1VN = new MMXU();
        MMXU mmxu2SN = new MMXU();
        MMXU mmxu3NN = new MMXU();
        MHAI mhai1VN = new MHAI();
        MHAI mhai2SN = new MHAI();
        MHAI mhai3NN = new MHAI();




        XCBR XCBR = new XCBR();
        CSWI CSWI = new CSWI();
        MSQI MSQI = new MSQI();
        RDIR RDIR = new RDIR();
        PTRC PTRC = new PTRC();

        lsvc.readComtrade("E:\\Idea\\Алгоритмы\\Алгоритмы ЛР№4\\Опыты\\DPT\\Trans3Obm\\Trans3ObmVneshABC");

        mmxu1VN.setInstIa(lsvc.getSignals().get(0));
        mmxu1VN.setInstIb(lsvc.getSignals().get(1));
        mmxu1VN.setInstIc(lsvc.getSignals().get(2));

        mmxu2SN.setInstIa(lsvc.getSignals().get(3));
        mmxu2SN.setInstIb(lsvc.getSignals().get(4));
        mmxu2SN.setInstIc(lsvc.getSignals().get(5));

        mmxu3NN.setInstIa(lsvc.getSignals().get(6));
        mmxu3NN.setInstIb(lsvc.getSignals().get(7));
        mmxu3NN.setInstIc(lsvc.getSignals().get(8));



        mhai1VN.setInstIa(lsvc.getSignals().get(0));
        mhai1VN.setInstIb(lsvc.getSignals().get(1));
        mhai1VN.setInstIc(lsvc.getSignals().get(2));

        mhai2SN.setInstIa(lsvc.getSignals().get(3));
        mhai2SN.setInstIb(lsvc.getSignals().get(4));
        mhai2SN.setInstIc(lsvc.getSignals().get(5));

        mhai3NN.setInstIa(lsvc.getSignals().get(6));
        mhai3NN.setInstIb(lsvc.getSignals().get(7));
        mhai3NN.setInstIc(lsvc.getSignals().get(8));

        rmxu.getInputA().add(mmxu1VN.getA());
        rmxu.getInputA().add(mmxu2SN.getA());
        rmxu.getInputA().add(mmxu3NN.getA());
        rmxu.getTmASt().getCrvPvs().add(new Point(0f,1000f));
        rmxu.getTmASt().getCrvPvs().add(new Point(2f,1000f));
        rmxu.getTmASt().getCrvPvs().add(new Point(1000f,2000f));
        rmxu.process();

        pdif.getInputHarm().add(mhai1VN.getHA());
        pdif.getInputHarm().add(mhai2SN.getHA());
        pdif.getInputHarm().add(mhai3NN.getHA());
        pdif.setDifAClc(rmxu.getDifoutput().get(0));
        pdif.setTripPoint(rmxu.getTripPoint());
        pdif.getStrVal().getSetMag().getF().setValue(300f);
        pdif.getMinOpTmms().setSetVal(5);
        pdif.getStrHarm().getSetMag().getF().setValue(0.02f);



        CSWI.getPos().getCtlVal().setValue(true);
        CSWI.getPos().getStVal().setValue((byte) 2);

        PTRC.getOp().add(pdif.getOp());

        CSWI.setOpOpn(PTRC.getTr());

        CSWI.setOpCls(PTRC.getTr());

        XCBR.setPos(CSWI.getPos());


        nhmiA.addSignals(
                new NHMISignal("IaVN", lsvc.getSignals().get(0).getInstMag().getF())

        );
        nhmiA.addSignals(
                new NHMISignal("IbBN", lsvc.getSignals().get(1).getInstMag().getF())

                );
        nhmiA.addSignals(
                new NHMISignal("IcBN", lsvc.getSignals().get(2).getInstMag().getF())

                );
        nhmiA.addSignals(
                new NHMISignal("IaVN50", mhai1VN.getHA().getPhsAHar().get(1).getMag().getF())

        );
        nhmiA.addSignals(
                new NHMISignal("IaVN100", mhai1VN.getHA().getPhsAHar().get(2).getMag().getF())

        );
        nhmiA.addSignals(
                new NHMISignal("IaVN250", mhai1VN.getHA().getPhsAHar().get(5).getMag().getF())

        );
        nhmiA.addSignals(
                new NHMISignal("IaDeyst", mmxu1VN.getA().getPhsA().getCVal().getMag().getF())

        );
        nhmiA.addSignals(
                new NHMISignal("IbDeyst", mmxu1VN.getA().getPhsB().getCVal().getMag().getF())

        );
        nhmiA.addSignals(
                new NHMISignal("IcDeyst", mmxu1VN.getA().getPhsC().getCVal().getMag().getF())

        );




        nhmiB.addSignals(
                new NHMISignal("IaSN", lsvc.getSignals().get(3).getInstMag().getF())

        );
        nhmiB.addSignals(
                new NHMISignal("IbSN", lsvc.getSignals().get(4).getInstMag().getF())

        );
        nhmiB.addSignals(
                new NHMISignal("IcSN", lsvc.getSignals().get(5).getInstMag().getF())

        );
        nhmiB.addSignals(
                new NHMISignal("IaSN50", mhai2SN.getHA().getPhsAHar().get(1).getMag().getF())

        );
        nhmiB.addSignals(
                new NHMISignal("IaSN250", mhai2SN.getHA().getPhsAHar().get(5).getMag().getF())

        );nhmiB.addSignals(
                new NHMISignal("IaDeyst", mmxu2SN.getA().getPhsA().getCVal().getMag().getF())

        );
        nhmiB.addSignals(
                new NHMISignal("IbDeyst", mmxu2SN.getA().getPhsB().getCVal().getMag().getF())

        );
        nhmiB.addSignals(
                new NHMISignal("IcDeyst", mmxu2SN.getA().getPhsC().getCVal().getMag().getF())

        );



        nhmiC.addSignals(
                new NHMISignal("IaNN", lsvc.getSignals().get(6).getInstMag().getF())

        );
        nhmiC.addSignals(
                new NHMISignal("IbNN", lsvc.getSignals().get(7).getInstMag().getF())

        );
        nhmiC.addSignals(
                new NHMISignal("IcNN", lsvc.getSignals().get(8).getInstMag().getF())

        );
        nhmiC.addSignals(
                new NHMISignal("IaNN50", mhai3NN.getHA().getPhsAHar().get(1).getMag().getF())

        );
        nhmiC.addSignals(
                new NHMISignal("IaNN250", mhai3NN.getHA().getPhsAHar().get(5).getMag().getF())

        );
        nhmiC.addSignals(
                new NHMISignal("IaDeyst", mmxu3NN.getA().getPhsA().getCVal().getMag().getF())

        );
        nhmiC.addSignals(
                new NHMISignal("IbDeyst", mmxu3NN.getA().getPhsB().getCVal().getMag().getF())

        );
        nhmiC.addSignals(
                new NHMISignal("IcDeyst", mmxu3NN.getA().getPhsC().getCVal().getMag().getF())

        );



        nhmiDif.addSignals(
                new NHMISignal("IADIF",rmxu.getDifoutput().get(0).getPhsA().getCVal().getMag().getF()),

                new NHMISignal("TripPoint",rmxu.getTripPoint().getPhsA().getCVal().getMag().getF())
        );
        nhmiDif.addSignals(
                new NHMISignal("IBDIF",rmxu.getDifoutput().get(0).getPhsB().getCVal().getMag().getF()),

                new NHMISignal("TripPoint",rmxu.getTripPoint().getPhsA().getCVal().getMag().getF())
        );
        nhmiDif.addSignals(
                new NHMISignal("ICDIF",rmxu.getDifoutput().get(0).getPhsC().getCVal().getMag().getF()),

                new NHMISignal("TripPoint",rmxu.getTripPoint().getPhsA().getCVal().getMag().getF())
        );
        nhmiDif.addSignals(
                new NHMISignal("Srabativanie",pdif.getOp().getGeneral())
        );
        nhmiDif.addSignals(
                new NHMISignal("Block",pdif.getBlockA())
        );
        nhmiDif.addSignals(
                new NHMISignal("Block",pdif.getBlockB())
        );
        nhmiDif.addSignals(
                new NHMISignal("Block",pdif.getBlockC())
        );
        nhmiDif.addSignals(
                new NHMISignal("Breaker",XCBR.getPos().getStVal())
        );









        while(lsvc.hasNext()) {
            lsvc.process();
            nhmiA.process();
            nhmiB.process();
            nhmiC.process();
            nhmiDif.process();
            mmxu1VN.process();
            mmxu2SN.process();
            mmxu3NN.process();
            mhai1VN.process();
            mhai2SN.process();
            mhai3NN.process();
            rmxu.process();
            pdif.process();
            CSWI.process();
            XCBR.process();
            MSQI.process();
            RDIR.process();

            PTRC.process();
        }
    }
}
