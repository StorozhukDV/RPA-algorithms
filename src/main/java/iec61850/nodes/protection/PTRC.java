package iec61850.nodes.protection;


import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.ACT;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter @Setter
public class PTRC extends LN {

    private ACT Tr = new ACT();

    private ArrayList<ACT> Op = new ArrayList<ACT>();


    @Override
    public void process() {
        for (int i=0; i < Op.size(); i++){
            if (Op.get(i).getGeneral().getValue()){
                Tr.getGeneral().setValue(true);
                break;
            }
            else{
                Tr.getGeneral().setValue(false);
            }
        }
    }
}
