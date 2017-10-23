package domainobject;

public class ExerciseEnv {

    private static final String DOMAIN = "radiant-gorge-83016.herokuapp.com";
    private static final String PORT = "443";

    public static String getUrl(){
        return "https://" + DOMAIN + ":" + PORT;
    }

}
