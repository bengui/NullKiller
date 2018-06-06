# NullKiller

A humble attempt to simplify the code for the common null check scenarios.

# Examples

Taking advantage of the Java 8 method reference you can make your code look cleaner with the `ifNotNull` function.
You can also define an "else" scenario with `ifNull` when the `response` object is `null.`
```java
import static com.bengui.nullkiller.NullKiller.ifNotNull;

public class Example {
    public void onEvent(String response) {
        ifNotNull(response, this::doSomeStuff)
                .ifNull(this::displayError);
    }

    private void doSomeStuff(String string) {
        // Do some stuff
    }

    private void displayError() {
        // Display error
    }
}
```

With the `ifFirstNotNull` you can have a safe callback to operate with the first element of a list since this method will check
 that a `List` is not `null` and is not `empty` and the first element is not `null` and then it will call to the callback.
 You will also have the option to define the function `ifNull` to provide an action if any of the validations fail.

```java
import java.util.List;
import static com.bengui.nullkiller.NullKiller.ifFirstNotNull;

public class Examples {

    public void onEvent(List<String> list) {
        ifFirstNotNull(list, this::doSomeStuff)
                .ifNull(this::displayError);
    }

    private void doSomeStuff(String string) {
        // Do some stuff
    }

    private void displayError() {
        // Display error
    }
}
```

With the `or` function you can check if an object is `null` and return a default value in that scenario, 
or the original object if it is not null.
```java
import static com.bengui.nullkiller.NullKiller.or;

public class Example {
    private Monster monster;

    public Monster getMonster() {
        return or(monster, new Monster());
    }

    public static class Monster {
        private String name;

        public String getName() {
            return name;
        }
    }
}
```