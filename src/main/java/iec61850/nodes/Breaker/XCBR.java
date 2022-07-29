package iec61850.nodes.Breaker;


import iec61850.nodes.Breaker.Command.DPC;
import iec61850.nodes.Breaker.Status.SPC;
import iec61850.nodes.common.LN;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class XCBR extends LN {
    private DPC Pos = new DPC();
    private SPC BlkOpn = new SPC();
    private SPC BlkCls = new SPC();

    @Override
    public void process() {
        if(!Pos.getCtlVal().getValue() && Pos.getStVal().getValue() == 2){
            Pos.getStVal().setValue((byte) 1);
        }
        if(Pos.getCtlVal().getValue() && Pos.getStVal().getValue() == 1){
            Pos.getStVal().setValue((byte) 2);
        }
    }
}
