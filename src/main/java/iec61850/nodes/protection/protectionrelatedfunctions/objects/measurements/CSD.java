package iec61850.nodes.protection.protectionrelatedfunctions.objects.measurements;


import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.Attribute;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.Point;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CSD {
    private ArrayList<Point> crvPvs = new ArrayList<>();

    private Attribute<Integer> numPts = new Attribute<>(0);
}
