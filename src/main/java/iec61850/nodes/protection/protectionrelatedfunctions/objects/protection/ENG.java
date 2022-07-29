package iec61850.nodes.protection.protectionrelatedfunctions.objects.protection;


import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.Attribute;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.protection.dir.Direction;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ENG {
    private Attribute<Direction> stVal = new Attribute<>(Direction.UNKNOWN);
}
