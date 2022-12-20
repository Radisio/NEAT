import org.junit.Test;

public class OtherTest {

    @Test
    public void mathAbsTest(){
        double error = 1-(Math.abs(1.0-1.0));
        System.out.println(error);
        assert error ==1;
    }
}
