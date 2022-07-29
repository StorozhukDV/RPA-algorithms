package iec61850.nodes.measurements;

import iec61850.nodes.common.LN;
import iec61850.nodes.measurements.filter.Filter;
import iec61850.nodes.measurements.filter.MSD;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.DEL;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.Vector;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.WYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.MV;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MMXU extends LN {

    private MV TotW = new MV();
    private MV TotVAr = new MV();
    private MV TotVA = new MV();
    private MV TotPF = new MV();

    private SAV instIa = new SAV();
    private SAV instIb = new SAV();
    private SAV instIc = new SAV();

    private SAV instUa = new SAV();
    private SAV instUb = new SAV();
    private SAV instUc = new SAV();

    private WYE A = new WYE();
    private WYE PhV = new WYE();
    private WYE W  = new WYE();
    private WYE Z  = new WYE();
    private DEL Zf = new DEL();

    private Filter iaF = new MSD();
    private Filter ibF = new MSD();
    private Filter icF = new MSD();

    private Vector Iab = new Vector();
    private Vector Ibc = new Vector();
    private Vector Ica = new Vector();

    private Filter UaF = new MSD();
    private Filter UbF = new MSD();
    private Filter UcF = new MSD();

    private Vector Uab = new Vector();
    private Vector Ubc = new Vector();
    private Vector Uca = new Vector();

    private float cosFiAB;
    private float cosFiBC;
    private float cosFiCA;
    private float sinFiAB;
    private float sinFiBC;
    private float sinFiCA;

    private Vector zA = new Vector();
    private Vector zB = new Vector();
    private Vector zC = new Vector();

    private Vector Zab = new Vector();
    private Vector Zbc = new Vector();
    private Vector Zca = new Vector();


    @Override
    public void process() {

        iaF.process(instIa, A.getPhsA().getCVal());
        ibF.process(instIb, A.getPhsB().getCVal());
        icF.process(instIc, A.getPhsC().getCVal());

        UaF.process(instUa, PhV.getPhsA().getCVal());
        UbF.process(instUb, PhV.getPhsB().getCVal());
        UcF.process(instUc, PhV.getPhsC().getCVal());

        Uab.ToVector((PhV.getPhsA().getCVal().getX().getF().getValue() - PhV.getPhsB().getCVal().getX().getF().getValue()),
                (PhV.getPhsA().getCVal().getY().getF().getValue() - PhV.getPhsB().getCVal().getY().getF().getValue()));
        Ubc.ToVector((PhV.getPhsB().getCVal().getX().getF().getValue() - PhV.getPhsC().getCVal().getX().getF().getValue()),
                (PhV.getPhsB().getCVal().getY().getF().getValue() - PhV.getPhsC().getCVal().getY().getF().getValue()));
        Uca.ToVector((PhV.getPhsC().getCVal().getX().getF().getValue() - PhV.getPhsA().getCVal().getX().getF().getValue()),
                (PhV.getPhsC().getCVal().getY().getF().getValue() - PhV.getPhsA().getCVal().getY().getF().getValue()));

        Iab.ToVector((A.getPhsA().getCVal().getX().getF().getValue() - A.getPhsB().getCVal().getX().getF().getValue()),
                (A.getPhsA().getCVal().getY().getF().getValue() - A.getPhsB().getCVal().getY().getF().getValue()));
        Ibc.ToVector((A.getPhsB().getCVal().getX().getF().getValue() - A.getPhsC().getCVal().getX().getF().getValue()),
                (A.getPhsB().getCVal().getY().getF().getValue() - A.getPhsC().getCVal().getY().getF().getValue()));
        Ica.ToVector((A.getPhsC().getCVal().getX().getF().getValue() - A.getPhsA().getCVal().getX().getF().getValue()),
                (A.getPhsC().getCVal().getY().getF().getValue() - A.getPhsA().getCVal().getY().getF().getValue()));

        Zab.Zvector(Iab, Uab);
        Zbc.Zvector(Ibc, Ubc);
        Zca.Zvector(Ica, Uca);

        Zab.Artog(Zab.getMag().getF().getValue(), Zab.getAng().getF().getValue());
        Zbc.Artog(Zbc.getMag().getF().getValue(), Zbc.getAng().getF().getValue());
        Zca.Artog(Zca.getMag().getF().getValue(), Zca.getAng().getF().getValue());

        Zf.getPhsAB().setCVal(Zab);
        Zf.getPhsBC().setCVal(Zbc);
        Zf.getPhsCA().setCVal(Zca);

        float angAB = (float) Math.toRadians(Uab.getAng().getF().getValue() - Iab.getAng().getF().getValue());
        float angBC = (float) Math.toRadians(Ubc.getAng().getF().getValue() - Ibc.getAng().getF().getValue());
        float angCA = (float) Math.toRadians(Uca.getAng().getF().getValue() - Ica.getAng().getF().getValue());

        cosFiAB= (float) Math.cos(angAB);
        cosFiBC= (float) Math.cos(angBC);
        cosFiCA= (float) Math.cos(angCA);

        sinFiAB= (float) Math.sin(angAB);
        sinFiBC= (float) Math.sin(angBC);
        sinFiCA= (float) Math.sin(angCA);

//        Zab.getX().getF().setValue(Zab.getMag().getF().getValue()*cosFiAB);
//        Zab.getY().getF().setValue(Zab.getMag().getF().getValue()*sinFiAB);

        zA.Zvector(A.getPhsA().getCVal(), PhV.getPhsA().getCVal());
        zB.Zvector(A.getPhsB().getCVal(), PhV.getPhsB().getCVal());
        zC.Zvector(A.getPhsC().getCVal(), PhV.getPhsC().getCVal());

        Z.getPhsA().setCVal(zA);
        Z.getPhsB().setCVal(zB);
        Z.getPhsC().setCVal(zC);


    }
}
