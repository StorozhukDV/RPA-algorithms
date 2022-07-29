package iec61850.nodes.protection.protectionrelatedfunctions.objects.protection;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.AnalogValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ASG {
    private AnalogValue SetMag = new AnalogValue();

}