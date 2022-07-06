package com.company;

import java.util.ArrayList;
import java.util.List;

public class Path {
    String name;
    List<Method> methods=new ArrayList<Method>();

    @Override
    public String toString() {
        String res= "  /" + name + ":\n";
        for (var method: methods
             ) {
            res+=method.toString();
        }
        return res;
    }
}
