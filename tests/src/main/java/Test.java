import java.util.regex.Pattern;

public class Test {

    private static final Pattern PARAMETER_REGEX = Pattern.compile("<[a-zA-Z0-9]+>");

    public static void main(String[] args) {
        System.out.println(deineMumHatEinVideoAufgenommen(Test1.class, "test1"));
    }

    public static <T extends Enum<T>> T deineMumHatEinVideoAufgenommen(Class<T> enumClass, String s) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().equalsIgnoreCase(s)) {
                return enumConstant;
            }
        }
        return null;
    }


    enum Test1 {
        TEST1,
        TEST2;
    }


}
