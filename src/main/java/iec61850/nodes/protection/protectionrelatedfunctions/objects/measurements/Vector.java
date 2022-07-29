package iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.AnalogValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Vector {

    private AnalogValue mag = new AnalogValue();
    private AnalogValue ang = new AnalogValue();
    private AnalogValue x = new AnalogValue();
    private AnalogValue y = new AnalogValue();
    private AnalogValue rad = new AnalogValue();

    public void Artog(float ampl, float ugl) {
        mag.getF().setValue(ampl);
        ang.getF().setValue(ugl);
        rad.getF().setValue((float) Math.toRadians(ang.getF().getValue()));
        x.getF().setValue((float) (ampl * Math.cos(rad.getF().getValue())));
        y.getF().setValue((float) (ampl * Math.sin(rad.getF().getValue())));
    }


    public void ToVector(float ampl, float ugl){
        x.getF().setValue(ampl);
        y.getF().setValue(ugl);
        rad.getF().setValue((float) Math.atan2( ugl ,ampl));
        mag.getF().setValue((float) Math.sqrt(ampl*ampl + ugl*ugl));
        ang.getF().setValue((float) Math.toDegrees(rad.getF().getValue()));
    }

    public void Zvector(Vector current, Vector voltage){
        ang.getF().setValue(voltage.getAng().getF().getValue() - current.getAng().getF().getValue());
        mag.getF().setValue(voltage.getMag().getF().getValue()/current.getMag().getF().getValue());
    }

    public void Slozhenie(Vector a, Vector b, Vector c){
        a.Artog(a.getMag().getF().getValue(),a.getAng().getF().getValue());
        b.Artog(b.getMag().getF().getValue(),b.getAng().getF().getValue());
        c.Artog(c.getMag().getF().getValue(),c.getAng().getF().getValue());
        x.getF().setValue(a.getX().getF().getValue() + b.getX().getF().getValue() + c.getX().getF().getValue());
        y.getF().setValue(a.getY().getF().getValue() + b.getY().getF().getValue() + c.getY().getF().getValue());
    }
}

