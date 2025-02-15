package nl.craftsmen.brewery.company;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CompanyApplication {

    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
