package iec61850.nodes.protection.protectionrelatedfunctions;


import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.WYE;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.ACD;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RDIR extends LN {
    private ACD Dir = new ACD();
    private WYE W = new WYE();
    private WYE Z = new WYE();


    @Override
    public void process() {
        if(Z.getPhsA().getCVal().getAng().getF().getValue() < -25 && Z.getPhsA().getCVal().getAng().getF().getValue() > -155){
            Dir.getDirPhsA().setValue(Direction.BACKWARD);
        }
        else{
            Dir.getDirPhsA().setValue(Direction.FORWARD);
        }
        if(Z.getPhsB().getCVal().getAng().getF().getValue() < -25 && Z.getPhsB().getCVal().getAng().getF().getValue() > -155){
            Dir.getDirPhsB().setValue(Direction.BACKWARD);
        }
        else{
            Dir.getDirPhsB().setValue(Direction.FORWARD);
        }
        if(Z.getPhsC().getCVal().getAng().getF().getValue() < -25 && Z.getPhsC().getCVal().getAng().getF().getValue() > -155){
            Dir.getDirPhsC().setValue(Direction.BACKWARD);
        }
        else{
            Dir.getDirPhsC().setValue(Direction.FORWARD);
        }
    }
}
