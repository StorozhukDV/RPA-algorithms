package iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DEL {
    private CMV phsAB = new CMV();
    private CMV phsBC = new CMV();
    private CMV phsCA = new CMV();
}
