package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Method {
    private String name;
    Set<Parameter> parameters=new HashSet<Parameter>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public String toString() {
        String res="    "+name + ":\n";
        if (!parameters.isEmpty()){
            res+="      parameters:\n";
            for (var parameter: parameters
                 ) {
                res+=parameter.toString();
            }
        }

        return res+Main.responses;
    }
}
