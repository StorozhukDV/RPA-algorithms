package iec61850.nodes.Breaker.Command;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class DPC {
    private Attribute<Byte> stVal = new Attribute<>((byte) 0);
    private Attribute<Boolean> ctlVal = new Attribute<>(false);
}
