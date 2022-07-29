package iec61850.nodes.protection.protectionrelatedfunctions.objects.samples;


import lombok.Getter;
import lombok.Setter;

/**
 * @author Александр Холодов
 * @created 03.2022
 * @project Programming2022
 * @description
 */
@Getter @Setter
public class AnalogValue {

    private Attribute<Float> f = new Attribute<>(0f);

}