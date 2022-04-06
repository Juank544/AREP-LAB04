package co.edu.escuelaing;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class Other
{
    public static void main( String[] args )
    {
        port(getPort());
        secure(getKeyStore(), "12345kpass", "keystores/myTrustStore", "12345tpass");
        get("/access", (request, response) -> "Ingreso Exitoso");
    }

    private static int getPort(){
        if (System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001;
    }

    private static String getKeyStore(){
        if (System.getenv("KEYSTORE") != null){
            return System.getenv("KEYSTORE");
        }
        return "keystores/ecikeystore.p12";
    }
}
