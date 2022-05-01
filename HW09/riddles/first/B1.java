package il.ac.tau.cs.sw1.ex9.riddles.first;

public class B1 extends A1 {
	@Override
    protected boolean foo(){
	    C1 c = new C1();
	    return !c.foo();
    }
}
