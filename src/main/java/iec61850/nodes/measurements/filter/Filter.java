package iec61850.nodes.measurements.filter;

import iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements.Vector;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.SAV;

/**
 * @author Александр Холодов
 * @created 03.2022
 * @project Programming2022
 * @description
 */
public abstract class Filter {

    public abstract void process(SAV sav, Vector vector);

}
