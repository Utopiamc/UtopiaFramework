import com.google.inject.*;
import com.google.inject.Module;

public class Test {

    public static void main(String[] args) {

        Module m = new AbstractModule() {
            @Override
            protected void configure() {
                binder().requireExplicitBindings();
            }
        };

        Module m1 = new AbstractModule() {
            @Override
            protected void configure() {
                bind(Test.class).asEagerSingleton();
            }
        };

        Injector injector = Guice.createInjector(m);

        Injector child1 = injector.createChildInjector(m, m1);
        Injector child2 = injector.createChildInjector(m);

        System.out.println(child1.getInstance(Test.class));
        System.out.println(child2.getInstance(Test.class));
        System.out.println(injector.getInstance(Test.class));

    }

}
