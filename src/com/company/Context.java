package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Context {
    String name;
    List<Path> paths=new ArrayList<Path>();

    @Override
    public String toString() {
        String res=name +"\n" +"paths:\n";
        for (var path : paths
             ) {
            res+=path.toString();
        }
        return res;
    }
}
