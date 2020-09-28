package ttl.introkot.course._2ClassesEtc;

import ttl.introkot.course._2ClassesEtc.MyClass;

/**
 * @author whynot
 */
class ObjectsFromJava {

    public static void main(String[] args) {
        MyClass.Companion.fakeStaticFun();
//        MyClass.fakeStaticFun();        //Need @JVMStatic on the function to make this work

        MyClass mc = new MyClass();
        MyClass.A.INSTANCE.callFooFromA(mc);
    }

}

