package Bazhanau.Logging;

public class ConsoleCatcher implements ICatcher {
    @Override
    public void catchException(Exception e) {
        System.err.println(e.toString());
    }
}
