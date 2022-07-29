package iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.posledovatelnosti;

import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.Vector;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.WYE;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MSQI extends LN {
    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();

    private WYE ImbA = new WYE();
    private WYE ImbV = new WYE();

    private Vector A = new Vector();
    private Vector B = new Vector();
    private Vector C = new Vector();

    @Override
    public void process() {
        Posled(ImbA, SeqA);
        Posled(ImbV, SeqV);

    }
    private void Posled(WYE vhodn, SEQ vihodn){
        //прямая последовательность и повороты на 120
        A.Artog(vhodn.getPhsA().getCVal().getMag().getF().getValue(),
                vhodn.getPhsA().getCVal().getAng().getF().getValue());

        B.Artog(vhodn.getPhsB().getCVal().getMag().getF().getValue(),
                vhodn.getPhsB().getCVal().getAng().getF().getValue() - 120);

        C.Artog(vhodn.getPhsC().getCVal().getMag().getF().getValue(),
                vhodn.getPhsC().getCVal().getAng().getF().getValue() + 120);

        vihodn.getC1().getCVal().ToVector(
                (A.getX().getF().getValue() +
                                        B.getX().getF().getValue() +
                                        C.getX().getF().getValue())/3,

                (A.getY().getF().getValue() +
                                        B.getY().getF().getValue() +
                                         C.getY().getF().getValue())/3);

        //обратная последовательность и повороты на -120
        B.Artog(vhodn.getPhsB().getCVal().getMag().getF().getValue(),
                vhodn.getPhsB().getCVal().getAng().getF().getValue() + 120);

        C.Artog(vhodn.getPhsC().getCVal().getMag().getF().getValue(),
                vhodn.getPhsC().getCVal().getAng().getF().getValue() - 120);

        vihodn.getC2().getCVal().ToVector(
                (A.getX().getF().getValue() +
                        B.getX().getF().getValue() +
                        C.getX().getF().getValue())/3,

                (A.getY().getF().getValue() +
                        B.getY().getF().getValue() +
                        C.getY().getF().getValue())/3);

        //Нулевая последовательность
        B.Artog(vhodn.getPhsB().getCVal().getMag().getF().getValue(),
                vhodn.getPhsB().getCVal().getAng().getF().getValue());

        C.Artog(vhodn.getPhsC().getCVal().getMag().getF().getValue(),
                vhodn.getPhsC().getCVal().getAng().getF().getValue());

        vihodn.getC3().getCVal().ToVector(
                (A.getX().getF().getValue() +
                        B.getX().getF().getValue() +
                        C.getX().getF().getValue())/3,

                (A.getY().getF().getValue() +
                        B.getY().getF().getValue() +
                        C.getY().getF().getValue())/3);
    }
}
