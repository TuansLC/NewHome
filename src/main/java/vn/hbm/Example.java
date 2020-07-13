package vn.hbm;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import vn.hbm.core.common.IGsonBase;
import vn.hbm.oa.ZaloOaClient;
import java.util.HashMap;
import java.util.Map;

public class Example  implements IGsonBase {
    public static void main(String[] args) {
       try {
           ZaloOaClient client = new ZaloOaClient();
           String access_token = "dFAKOtWHSI-OyPHiU1Dt8ftma6b-3X8-XPRrTZ4iJpkUkEqsU1y9ByM1Z65H1IPvle2dHX4yC4QtfA9DEHe40eAwkImRNb9XgVZCKoLGPcw5zUD464vWVOFj-tTa1N9LugBLLquhDLxhpA1APqaGUOdUZJefO0qghSYG8n5vCXE8pvSzF7090DdAcJHXLdiPy_pQAX01GnIIXj8s7315F9sh_NCg67y-ahcOTpCj2M27h9Ht5XKCUwM4xqWt4tD3fgpFMY08LKwnaB1m01CcUvMGicT8HzRGqtfv37Kl";


           Map<String, Object> params = new HashMap<>();
           params.put("access_token", access_token);


           JsonObject default_action2 = new JsonObject();
           default_action2.addProperty("type", "oa.open.url");
           default_action2.addProperty("url", "http://iloto.vn/");

           JsonObject element2 = new JsonObject();
           element2.addProperty("title", "Xổ số phát lộc.");
           element2.addProperty("subtitle", "Zalo đang cập nhật một số API mới để hỗ trợ các đối tác và lập trình viên tốt hơn trong việc tích hợp với nền tảng Zalo & kinh doanh trên Zalo Shop.");
           element2.addProperty("image_url", "https://diembao.net/uploads/default/optimized/2X/f/fc7e7e277b75365cedb91a51dd98d6a839402afa_2_690x788.jpeg");
           element2.add("default_action", default_action2);

           JsonArray elements = new JsonArray();
           elements.add(element2);


           JsonObject payload = new JsonObject();
           payload.addProperty("template_type", "list");
           payload.add("elements", elements);

           JsonObject attachment = new JsonObject();
           attachment.addProperty("type", "template");
           attachment.add("payload", payload);

           JsonObject message = new JsonObject();

           message.add("attachment", attachment);

           JsonObject recipient = new JsonObject();
           recipient.addProperty("user_id", "4709041003359256758");

           JsonObject body = new JsonObject();
           body.add("recipient", recipient);
           body.add("message", message);

           JsonObject result = client.excuteRequest("https://openapi.zalo.me/v2.0/oa/message", "POST", params, body);



           System.out.println(body);
           System.out.println(result);
           String a = String.valueOf(result.get("error"));
           System.out.println(a);
           if (a.equals("0")) {
               System.out.println("Oke.");
           } else {
               System.out.println("Fail.");
           }

       } catch (Exception ex) {
           System.out.println(ex.toString());
       }
    }
}
