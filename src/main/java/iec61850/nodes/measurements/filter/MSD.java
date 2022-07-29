package iec61850.nodes.measurements.filter;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.Vector;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MSD extends Filter {

    private int size = 20;
    private int count = 0;
    private final float k = (float) (Math.sqrt(2)/size);

    private float x;
    private float y;
    private final float[] bufferX = new float[size];
    private final float[] bufferY = new float[size];
    private int Fz;


    @Override
    public void process(SAV sav, Vector vector) {

        x += k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * 50) *  (0.02/size) * count) - bufferX[count];
        y += k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * 50) * (0.02/size) * count) - bufferY[count];
        bufferX[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * 50) * (0.02/size) * count));
        bufferY[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * 50) * (0.02/size) * count));
        vector.ToVector(x, y);
        if(++count >= size) count = 0;
    }
}
