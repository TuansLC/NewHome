package vn.hbm.api;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.hbm.oa.ZaloOaClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/api")
public class TestApiController {
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public void getInfor() {
        try {
            ZaloOaClient client = new ZaloOaClient();
            String access_token = "dFAKOtWHSI-OyPHiU1Dt8ftma6b-3X8-XPRrTZ4iJpkUkEqsU1y9ByM1Z65H1IPvle2dHX4yC4QtfA9DEHe40eAwkImRNb9XgVZCKoLGPcw5zUD464vWVOFj-tTa1N9LugBLLquhDLxhpA1APqaGUOdUZJefO0qghSYG8n5vCXE8pvSzF7090DdAcJHXLdiPy_pQAX01GnIIXj8s7315F9sh_NCg67y-ahcOTpCj2M27h9Ht5XKCUwM4xqWt4tD3fgpFMY08LKwnaB1m01CcUvMGicT8HzRGqtfv37Kl";

            Map<String, Object> params = new HashMap<>();
            params.put("access_token", access_token);

            JsonObject data = new JsonObject();
            data.addProperty("offset", 0);
            data.addProperty("count", 5);
            params.put("data", data.toString());

            JsonObject result = client.excuteRequest("https://openapi.zalo.me/v2.0/oa/getfollowers", "GET", params, null);
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}

