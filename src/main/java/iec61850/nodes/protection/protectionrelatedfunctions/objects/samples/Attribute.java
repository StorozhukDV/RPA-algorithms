package iec61850.nodes.protection.protectionrelatedfunctions.objects.samples;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Attribute<T> {

    private T value;

    public Attribute(T value) {  this.value = value;  }
}
