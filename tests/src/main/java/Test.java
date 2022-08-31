import java.util.UUID;

public class Test {

    public static void main(String[] args) {

    }

    private interface SB {

        SBB addLine();

    }

    private interface SBB {

        void update(String value);

        void update(String value, UUID player);

    }


}
