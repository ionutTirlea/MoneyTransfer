package rest;

import org.apache.log4j.BasicConfigurator;

public class Application {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        RouteMapping.configure();
        ExceptionMapping.configure();
    }
}