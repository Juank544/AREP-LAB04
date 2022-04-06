package co.edu.escuelaing;

import co.edu.escuelaing.model.User;
import com.google.gson.Gson;

import java.util.HashMap;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class Login
{
    private static HashMap<String, String> users = new HashMap<>();
    private static Gson gson = new Gson();

    public static void main( String[] args )
    {
        secure(getKeyStore(), "kpass1234", "keystores/myTrustStore", "tpass1234");
        port(getPort());
        addUsers();
        staticFileLocation("/public");

        get("/hello", (request,response) -> "Hello!");

        get("/", (request,response) -> {
            response.redirect("/index.html");
            return null;
        });

        post("/login", (request,response) ->{
            request.session(true);
            User user = gson.fromJson(request.body(), User.class);
            if (users.containsKey(user.getEmail())){
                if (user.getPassword().equals(users.get(user.getPassword()))){
                    request.session().attribute("User", user.getEmail());
                    request.session().attribute("isLogged", true);
                }
                else {
                    return "Contraseña incorrecta";
                }
            }
            else {
                return "Usuario incorrecto";
            }
            response.redirect("/hello");
            return null;
        });
    }

    private static void addUsers(){
        users.put("juan@mail.com", "12345");
        users.put("camilo@mail.com", "98765");
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
