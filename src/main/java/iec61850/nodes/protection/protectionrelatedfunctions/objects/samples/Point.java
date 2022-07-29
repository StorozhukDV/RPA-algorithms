package iec61850.nodes.protection.protectionrelatedfunctions.objects.samples;


import lombok.Data;

@Data
public class Point {
    private Attribute<Float> xVal = new Attribute<>((float)0);
    private Attribute<Float> yVal = new Attribute<>((float)0);

    public Point(){}
    public Point(Float x, Float y){
        xVal.setValue(x);
        yVal.setValue(y);
    }

}
