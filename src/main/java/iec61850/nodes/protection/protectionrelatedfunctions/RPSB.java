package iec61850.nodes.protection.protectionrelatedfunctions;

import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.DEL;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.Vector;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.ACT;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.ASG;
import lombok.Data;

@Data
public class RPSB {
    private ACT Op = new ACT();
    private Vector Z = new Vector();

    private ASG SwgVal = new ASG();

    private int size = 10;
    private float[] buffer = new float[size];
    private float[] newbuffer = new float[size];
    private int count = 0;
    private float raznost = 0;



    public void process(Vector Z) {
        raznost = Math.abs(Z.getMag().getF().getValue()) - buffer[0];
        buffer[count] = Math.abs(Z.getMag().getF().getValue());
        count++;
        if(++count >= size) {
            count = 9;
            System.arraycopy(buffer, 1, newbuffer, 0, 9);
            buffer = newbuffer;
            if ((Math.abs(raznost)/2500) >= SwgVal.getSetMag().getF().getValue()){
                Op.getGeneral().setValue(true);
            }
            else{
                Op.getGeneral().setValue(false);
            }

        }
    }
}
