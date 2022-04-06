package co.edu.escuelaing;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        secure(getKeyStore(), "kpass1234", "keystores/myTrustStore", "tpass1234");
        port(getPort());
        get("/hello", (request,response) -> "Hello!");
    }

    private static int getPort(){
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }

    private static String getKeyStore(){
        if (System.getenv("KEYSTORE") != null){
            return System.getenv("KEYSTORE");
        }
        return "keystores/ecikeystore.p12";
    }
}
