package co.edu.escuelaing;

import co.edu.escuelaing.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;

import java.util.ArrayList;
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
            JsonObject json = new JsonObject();
            if (request.body() != null){
                ArrayList<String> responses = login(request);
                json.addProperty("status", 200);
                json.addProperty("result", responses.get(0));
                json.addProperty("response", responses.get(1));
                return json;
            }
            json.addProperty("status", 400);
            json.addProperty("result", "Error");
            json.addProperty("response", "Cannot log in");
            return json;
        });
    }

    private static ArrayList<String> login(Request request){
        ArrayList<String> reponses = new ArrayList<String>();
        User user = gson.fromJson(request.body(), User.class);

        if (users.containsKey(user.getEmail())){
            if (users.get(user.getEmail()).equals(user.getPassword())){
                request.session(true);
                request.session().attribute("User", user.getEmail());
                request.session().attribute("isLogged", true);
                reponses.add("You're logged");
                return reponses;
            }
            reponses.add("Password incorrect");
            return reponses;
            }
        reponses.add("Email incorrect");
        return reponses;
        }
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
