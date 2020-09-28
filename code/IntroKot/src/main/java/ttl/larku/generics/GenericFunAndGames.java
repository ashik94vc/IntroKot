package ttl.larku.generics;

import java.util.function.Function;

/**
 * An attempt to explain why the bounds are used in
 * signatures like Function<? super T, ? extends R>
 *
 * @author whynot
 */
public class GenericFunAndGames {

    public void go() {
        MyThing<Number> numberThing = new MyThing<>(22.3);
        MyThing<Integer> integerThing = new MyThing<>(22);

        //The types are the important things to keep track of
        //in this examples.  We say a function maps from the
        //first type (the type of the argument) to the second type
        //(the return type).  The names of the functions reflect
        //the mappings  e.g from Number to Number
        Function<Number, Number> numberToNumber = (n) -> 2.3;
        Function<Integer, Number> integerToNumber = (n) -> 2;

        Function<Number, Integer> numberToInteger = (n) -> 2;
        Function<Integer, Integer> integerToInteger = (n) -> 2;

        //This is straight ahead T's and R's, so it
        //would work without the ? super T or ? extends R
        MyThing<Number> nt1 = numberThing.map(numberToNumber, 10);
        MyThing<Integer> it1 = integerThing.map(integerToInteger, 10);

        //This one won't work without the ? super T.  T here is Integer,
        //because integerThing is MyThing<Integer>.  We are trying to
        //pass in a function which takes a Number, i.e. a ? super Integer
        //Which is a perfectly valid thing to do, but we are not allowed
        //to do it unless we tell the compiler it is okay with the
        //? super T
        MyThing<Integer> it2 = integerThing.map(numberToInteger, 20);

        //This one won't work without the ? extends R
        //R is derived from whatever return type you are
        //trying to capture, so 'Number' in this case
        //Our function returns an Integer, i.e. a ? extends Number
        //We need to tell the compiler that is okay with ? extends R
        MyThing<Number> nt3 = numberThing.map(numberToInteger, 10);
        //Same as above, but this one *will* work without
        //the ? extends R, because we are not capturing the
        //return, so there is no R for the compiler to check.
        numberThing.map(numberToInteger, 10);
        //We could make it break by being explicit
        numberThing.<Number>map(numberToInteger, 10);

        //Using method refrences

        MyThing<Number> nt4 = numberThing.map(this::fun2, 20);
        MyThing<Number> nt5 = numberThing.map(this::fun2, 20);

        //This next one will not compile.  Our T is Number and
        //we are trying to call a function which takes an Integer,
        // i.e. a ? extends Number.  Which is a no no no no no no.
        //MyThing<Number> ntBad = numberThing.map(this::fun3, 20); //<-- will not compile


        MyThing<String> s = numberThing.map(this::fun4, 10);
        MyThing<String> s2 = numberThing.map((n) -> "" + n.intValue(), 10);

    }

    class MyThing<T> {
        private final T t;

        public MyThing(T t) {
            this.t = t;
        }
        public <R> MyThing<R> map(Function<? super T, ? extends R> mapper, T t) {
            return new MyThing<R>(mapper.apply(t));
        }
    }


    public Integer fun3(Integer n) {
        return n.intValue() * n.intValue();
    }

    public Number funo(Object o) {
        return 10;
    }

    public Number fun(Number n) {
        return n.intValue() * n.intValue();
    }

    public Integer fun2(Number n) {
        return n.intValue() * n.intValue();
    }

    public String fun4(Number n) {
        return ("" + n.intValue() * n.intValue());
    }
}
