package iec61850.nodes.Breaker.Status;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SPC {
    private Attribute<Boolean> stVal = new Attribute<>( false);
}
