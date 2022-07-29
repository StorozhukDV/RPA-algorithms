package iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.posledovatelnosti;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.CMV;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SEQ {
    private CMV c1 = new CMV();
    private CMV c2 = new CMV();
    private CMV c3 = new CMV();
}
