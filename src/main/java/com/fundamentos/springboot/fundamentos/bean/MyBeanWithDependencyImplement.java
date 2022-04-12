package com.fundamentos.springboot.fundamentos.bean;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class MyBeanWithDependencyImplement  implements MyBeanWithDependecy{

    Log LOOGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);
    private MyOperation myOperation; //call another dependency

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOOGER.info("Hemos ingresado al metodo printWithDependency");
        int numero =1;
        LOOGER.debug("El numero enviado como parametro a la dependencia operacion es: "+ numero);
        System.out.println(myOperation.sum(1));
        System.out.println("HI from a bean with dependence  ");
    }
}
