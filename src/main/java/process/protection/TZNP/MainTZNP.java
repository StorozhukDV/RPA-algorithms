package process.protection.TZNP;

import iec61850.nodes.Breaker.CSWI;
import iec61850.nodes.Breaker.XCBR;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.protection.*;
import iec61850.nodes.protection.protectionrelatedfunctions.RDIR;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.posledovatelnosti.MSQI;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;

public class MainTZNP {

    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        NHMI nhmi = new NHMI();
        MMXU mmxu = new MMXU();
        PTOC PTOC1 = new PTOC();
        PTOC PTOC2 = new PTOC();
        PTOC PTOC3 = new PTOC();

        PTOC PTOC1d = new PTOC();
        PTOC PTOC2d = new PTOC();
        XCBR XCBR = new XCBR();
        CSWI CSWI = new CSWI();
        MSQI MSQI = new MSQI();
        RDIR RDIR = new RDIR();
        PTRC PTRC = new PTRC();

        lsvc.readComtrade("E:\\Idea\\Алгоритмы\\Алгоритмы ЛР№2\\ЛР2\\Опыты\\KZ6");

        mmxu.setInstIa(lsvc.getSignals().get(5));
        mmxu.setInstIb(lsvc.getSignals().get(4));
        mmxu.setInstIc(lsvc.getSignals().get(3));
        mmxu.setInstUa((lsvc.getSignals().get(0)));
        mmxu.setInstUb(lsvc.getSignals().get(1));
        mmxu.setInstUc(lsvc.getSignals().get(2));

        MSQI.setImbA(mmxu.getA());
        MSQI.setImbV(mmxu.getPhV());

        RDIR.setZ(mmxu.getZ());

        //НЕНАПРАВЛЕННЫЕ СТУПЕНИ ТЗНП
        PTOC1.getA().setPhsA(MSQI.getSeqA().getC3());
        PTOC1.getA().setPhsB(MSQI.getSeqA().getC3());
        PTOC1.getA().setPhsC(MSQI.getSeqA().getC3());
        PTOC1.getStrVal().getSetMag().getF().setValue(1000F);
        PTOC1.getOpDITmms().setSetVal(0);


        PTOC2.getA().setPhsA(MSQI.getSeqA().getC3());
        PTOC2.getA().setPhsB(MSQI.getSeqA().getC3());
        PTOC2.getA().setPhsC(MSQI.getSeqA().getC3());
        PTOC2.getStrVal().getSetMag().getF().setValue(75F);
        PTOC2.getOpDITmms().setSetVal(250);


        PTOC3.getA().setPhsA(MSQI.getSeqA().getC3());
        PTOC3.getA().setPhsB(MSQI.getSeqA().getC3());
        PTOC3.getA().setPhsC(MSQI.getSeqA().getC3());
        PTOC3.getStrVal().getSetMag().getF().setValue(600F);
        PTOC3.getOpDITmms().setSetVal(500);


        //НАПРАВЛЕННЫЕ СТУПЕНИ ТЗНП:
        PTOC1d.getA().setPhsA(MSQI.getSeqA().getC3());
        PTOC1d.getA().setPhsB(MSQI.getSeqA().getC3());
        PTOC1d.getA().setPhsC(MSQI.getSeqA().getC3());
        PTOC1d.getStrVal().getSetMag().getF().setValue(1000F);
        PTOC1d.getOpDITmms().setSetVal(0);
        PTOC1d.getDirMod().getStVal().setValue(Direction.FORWARD);
        PTOC1d.setDir(RDIR.getDir());


        PTOC2d.getA().setPhsA(MSQI.getSeqA().getC3());
        PTOC2d.getA().setPhsB(MSQI.getSeqA().getC3());
        PTOC2d.getA().setPhsC(MSQI.getSeqA().getC3());
        PTOC2d.getStrVal().getSetMag().getF().setValue(75F);
        PTOC2d.getOpDITmms().setSetVal(250);
        PTOC2d.getDirMod().getStVal().setValue(Direction.FORWARD);
        PTOC2d.setDir(RDIR.getDir());

        CSWI.getPos().getCtlVal().setValue(true);
        CSWI.getPos().getStVal().setValue((byte) 2);

        PTRC.getOp().add(PTOC1d.getOp());
        PTRC.getOp().add(PTOC2d.getOp());
        PTRC.getOp().add(PTOC1.getOp());
        PTRC.getOp().add(PTOC2.getOp());
        PTRC.getOp().add(PTOC3.getOp());

        CSWI.setOpOpn(PTRC.getTr());

        CSWI.setOpCls(PTRC.getTr());

        if (!CSWI.getPos().getCtlVal().getValue()){
            PTOC2.getOpDITmms().setSetVal(0);
            PTOC3.getOpDITmms().setSetVal(0);
            PTOC2d.getOpDITmms().setSetVal(0);
        }

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
                new NHMISignal("Iпп", MSQI.getSeqA().getC1().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Iоп", MSQI.getSeqA().getC2().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Iнп", MSQI.getSeqA().getC3().getCVal().getMag().getF()),
                new NHMISignal("РТОС1", PTOC1.getStrVal().getSetMag().getF()),
                new NHMISignal("РТОС2", PTOC2.getStrVal().getSetMag().getF()),
                new NHMISignal("РТОС3", PTOC3.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Uпп", MSQI.getSeqV().getC1().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Uоп", MSQI.getSeqV().getC2().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Uнп", MSQI.getSeqV().getC2().getCVal().getMag().getF())
        );

//        nhmi.addSignals(
//                new NHMISignal("Фурье напруга А", mmxu.getPhV().getPhsA().getCVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Фурье ток А", mmxu.getA().getPhsA().getCVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Фурье ток B", mmxu.getA().getPhsB().getCVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Фурье ток C", mmxu.getA().getPhsC().getCVal().getMag().getF())
//        );
        nhmi.addSignals(
                new NHMISignal("Breaker", XCBR.getPos().getStVal())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PTOC1", PTOC1.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PTOC2", PTOC2.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PTOC3", PTOC3.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PTOC1d", PTOC1d.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Op.PTOC2d", PTOC2d.getOp().getGeneral())
        );

        while(lsvc.hasNext()) {
            lsvc.process();
            nhmi.process();
            mmxu.process();
            PTOC1d.process();
            PTOC2d.process();
            CSWI.process();
            XCBR.process();
            MSQI.process();
            RDIR.process();
            PTOC1.process();
            PTOC2.process();
            PTOC3.process();
            PTRC.process();
        }
    }
}
