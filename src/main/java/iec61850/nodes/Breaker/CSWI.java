package iec61850.nodes.Breaker;

import iec61850.nodes.Breaker.Command.DPC;
import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.ACT;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CSWI extends LN {
private ACT OpOpn  = new ACT();
private ACT OpCls = new ACT();

private DPC Pos = new DPC();
private DPC PosA = new DPC();
private DPC PosB = new DPC();
private DPC PosC = new DPC();

    @Override
    public void process() {
        if (OpOpn.getGeneral().getValue() && Pos.getStVal().getValue() == 2 ){
            Pos.getCtlVal().setValue(false);
        }
        if (!OpCls.getGeneral().getValue() && Pos.getStVal().getValue() == 1 ){
            Pos.getCtlVal().setValue(true);
        }
    }
}
