package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static String responses;

    public static void main(String[] args) {
        System.out.println("Path:");
        Scanner scanner = new Scanner(System.in);

        var initPathStr = scanner.next();//c:/try1/
        //String initPath = "OCOIntegration_api_corpds.xml";
        var initPath = initPathStr.replace("\\", "/");
        System.out.println(initPath);
        File folder = new File(initPath);
        for (File file : folder.listFiles()) {
            //System.out.println(file.getName());

            String url = "https://10.10.0.69:8253";
            responses = "      responses:\n" +
                    "        '200':\n" +
                    "          description: OK\n" +
                    "        '400':\n" +
                    "          description: Bad request.\n" +
                    "        '401':\n" +
                    "          description: Authorization information is missing.\n" +
                    "        '403':\n" +
                    "          description: Authorization information is invalid. \n" +
                    "        '404':\n" +
                    "          description: Not found.\n" +
                    "        '5XX':\n" +
                    "          description: Unexpected error.\n";


            Context currentContext = new Context();
            Path currentPath = null;
            Method currentMethod = null;
            System.out.println(initPath + file.getName());
            try (BufferedReader br = new BufferedReader(new FileReader(initPath +"/"+ file.getName()))) {
                String nameApi = file.getName().substring(0, (file.getName()).length() - 4);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getName().substring(0, file.getName().length() - 4) + ".swagger"))) {
                    String text = "openapi: 3.0.1\n" +
                            "info:\n" +
                            "  title: " + nameApi +
                            "\n  description: API Definition of " + nameApi +
                            "\n  version: 1.0.1\n" +
                            "servers:\n" +
                            "  - url: " + url + "/";
                    bw.write(text);
                    //чтение построчно
                    String str;
                    while ((str = br.readLine()) != null) {
                        if (str.contains("api context")) {
                            String contextName = str.split("api context=\"/")[1].split("\" name")[0];

                            currentContext.name = contextName;
                            //System.out.println(currentContext.name);
                        }
                        if (str.contains("<resource")) {
                            currentPath = new Path();
                            currentPath.name = nameApi;
                            currentContext.paths.add(currentPath);
                            if (str.contains("url-mapping")) {
                                currentPath.name = str.split("url-mapping=\"/")[1].split("\"")[0];
                            } else if (str.contains("uri-template")) {
                                currentPath.name = str.split("uri-template=\"/")[1].split("\"")[0];
                            }
                            String methodStr = "default";
                            if (str.contains("methods")) {
                                methodStr = str.split("methods=\"")[1].split("\"")[0];
                            }
                            String[] methods = methodStr.split(" ");
                            for (String methodName : methods
                            ) {
                                Method method = new Method();
                                method.setName(methodName);
                                currentPath.methods.add(method);
                                //System.out.println(currentPath.name);
                            }
                        }
                        if (str.contains("get-property('query.param.")) {

                            String paramName = str.split("get-property\\('query.param.")[1].split("'")[0].split("\\?")[0];
                            String paramIn = "query";
                            for (Method method : currentPath.methods
                            ) {
                                Parameter parameter = new Parameter();
                                parameter.name = paramName;
                                parameter.in = paramIn;
                                method.parameters.add(parameter);
                                // System.out.println(method.parameters);
                            }
                        }
                        if (str.contains("get-property('uri.var.")) {

                            String paramName = str.split("get-property\\('uri.var.")[1].split("'")[0];
                            String paramIn = "path";
                            for (Method method : currentPath.methods
                            ) {
                                Parameter parameter = new Parameter();
                                parameter.name = paramName;
                                parameter.in = paramIn;
                                method.parameters.add(parameter);
                                // System.out.println(method.parameters);
                            }
                        }
                    }
                    // System.out.println(currentContext);
                    bw.write(currentContext.toString());
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            //System.out.println(currentContext);
        }
    }
}
