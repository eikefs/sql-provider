package io.github.eikefs.sql.provider.test.orm;

public class ORMTest {

    public static void main(String[] args) {
        long current = System.currentTimeMillis();

        System.out.println(User.create());

        System.out.println(System.currentTimeMillis() - current);
    }

}
