package com.company;

import java.util.Objects;

public class Parameter {
    String name;
    String in;
    String required="true";
   // String type="string";

    @Override
    public String toString() {
        return "      - name: \"" + name + "\"\n" +
                "        in: \"" + in + "\"\n" +
                "        required: " + required + "\n" +
                "        schema:\n" +
                "          type: string\n" +
                "          format: string\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return name.equals(parameter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
