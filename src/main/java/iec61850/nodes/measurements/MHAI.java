package iec61850.nodes.measurements;

import iec61850.nodes.common.LN;
import iec61850.nodes.measurements.filter.Filter;
import iec61850.nodes.measurements.filter.Furier;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.HWYE;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class MHAI extends LN {

    private HWYE HA = new HWYE();
    private SAV instIa = new SAV();
    private SAV instIb = new SAV();
    private SAV instIc = new SAV();
    private ArrayList<Filter> iaF = new ArrayList<>();
    private ArrayList<Filter> ibF = new ArrayList<>();
    private ArrayList<Filter> icF = new ArrayList<>();



    @Override
    public void process() {
        for (int i=0;i<=HA.getNumHar().getValue();i++){
            iaF.add(new Furier(i));
            ibF.add(new Furier(i));
            icF.add(new Furier(i));

            iaF.get(i).process(instIa,HA.getPhsAHar().get(i));
            ibF.get(i).process(instIb,HA.getPhsBHar().get(i));
            icF.get(i).process(instIc,HA.getPhsCHar().get(i));

        }
    }
}
