package il.ac.tau.cs.sw1.ex9.riddles.third;

public class B3 extends A3 {
    public B3(String s) {
        super(s);
    }
    @Override
    public void foo(String s) throws Exception{
        if (!s.equals("")){
            throw new B3(this.s);
        }
    }
    public String getMessage(){
        return this.s;
    }
}