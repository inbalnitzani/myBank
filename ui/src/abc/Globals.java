package abc;

public class Globals {
    public static int worldTime = 1;

    public void setWorldTime(int time){
        worldTime=time;
    }
    public void changeWorldTimeBy(int timeUnitToChange) {
        worldTime += timeUnitToChange;
    }
}
