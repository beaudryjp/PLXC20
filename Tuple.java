public class Tuple{
    private String a;
    private String b;
    
    public Tuple(String x, String y){
        a = x;
        b = y;
    }

    public String getA(){
        return a;
    }

    public String getB(){
        return b;
    }


    public void setA(String c){
        a = c;
    }

    public void setB(String c){
        b = c;    
    }

    public String toString(){
        return "("+ a + ", " + b + ")";
    }
}
