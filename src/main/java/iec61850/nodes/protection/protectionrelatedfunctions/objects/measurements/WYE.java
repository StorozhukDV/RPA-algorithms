package iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Александр Холодов
 * @created 03.2022
 * @project Programming2022
 * @description
 */
@Getter @Setter
public class WYE {

    private CMV phsA = new CMV();
    private CMV phsB = new CMV();
    private CMV phsC = new CMV();
    private CMV phsNeut = new CMV();
}
