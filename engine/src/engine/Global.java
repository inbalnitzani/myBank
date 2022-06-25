package engine;

public class Global {
    public static int worldTime=1;
    public static void setWorldTime(int time){
        worldTime=time;
    }
    public static void changeWorldTimeByOne() {
        worldTime ++;
    }
}
