package rest.util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer {

    private JsonTransformer() {
    }

    private static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer asJson() {
        return JsonTransformer::toJson;
    }
}