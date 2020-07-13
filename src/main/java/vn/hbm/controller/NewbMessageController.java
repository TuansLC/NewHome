package vn.hbm.controller;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hbm.core.common.Common;
import vn.hbm.core.common.Constants;
import vn.hbm.core.common.IGsonBase;
import vn.hbm.core.common.MessageContants;
import vn.hbm.core.service.CommonService;
import vn.hbm.jpa.NewbMessageZalo;
import vn.hbm.jpa.NewbNews;
import vn.hbm.oa.ZaloOaClient;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/sendMessage")
public class NewbMessageController implements IGsonBase {
    private final Logger LOG = LoggerFactory.getLogger(NewbMessageZalo.class);

    @Autowired
    CommonService commonService;

    // GET LIST

    @RequestMapping(value = "/index.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String indexNews(HttpServletRequest request, Model model, Pageable pageable) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug(transId + "::BEGIN::index.html");
        try {
            NewbMessageZalo frm = new NewbMessageZalo();
            request.getSession().removeAttribute("formObj");
            initData(transId, model, pageable, "");
            model.addAttribute("formObj", frm);

        } catch (Exception ex) {
        }
        LOG.debug(transId + "::END::index.html");
        return "/message/text";
    }

    private void initData(String transId, Model model, Pageable pageable, String phone) {
        Page<NewbMessageZalo> pageData = null;
        Pageable _pageable = null;
        try {
            List<NewbMessageZalo> list = new ArrayList<>();
            list.addAll(getListData(transId, phone));
            _pageable = PageRequest.of(pageable.getPageNumber(), 10);
//            _pageable = PageRequest.of(pageable.getPageNumber(), 10,pageable.getSort().ascending());

            //Thuc hien phan trang cho danh sach
            int fromRecord = 0;
            int toRecord = 0;

            fromRecord = _pageable.getPageNumber() * pageable.getPageSize();
            toRecord = (_pageable.getPageNumber() + 1) * pageable.getPageSize();
            if (toRecord > list.size()) {
                toRecord = list.size();
            }

            List<NewbMessageZalo> lstResult = null;
            if (list != null && !list.isEmpty()) {
                lstResult = list.subList(fromRecord, toRecord);
            } else {
                lstResult = new ArrayList<>();
            }
//            Collections.reverse(lstResult);
            pageData = new PageImpl<>(lstResult, _pageable, list.size());
        } catch (Exception ex) {
            pageData = new PageImpl<>(new ArrayList<NewbMessageZalo>(), _pageable, 0);
        } finally {
            model.addAttribute("page", pageData);
        }
    }


    private List<NewbMessageZalo> getListData(String transId, String phone) {
        List<NewbMessageZalo> list = new ArrayList<>();
        try {
            String sql = " SELECT * FROM newb_zalo_text " +
                    " WHERE status = 1 " +
                    " AND (phone = ? OR ? = '');";
            List<Object[]> lstObj = commonService.findBySQL(transId, sql, new String[]{phone, phone});
            if (lstObj != null && !lstObj.isEmpty()) {
                lstObj.stream().forEach(item -> {
                    NewbMessageZalo bean = new NewbMessageZalo();
                    bean.setId(Integer.valueOf(item[0].toString()));
                    bean.setMessage(String.valueOf(item[1]));
                    bean.setPhone(String.valueOf(item[2]));
                    bean.setStatus(Integer.valueOf(item[3].toString()));
                    list.add(bean);
                });
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return list;
    }

    // CREATE
    @RequestMapping(value = "/create.html", method = {RequestMethod.GET})
    public String indexAdd(Locale locale, Model model) {
        LOG.debug("BEGIN::create.html");
        try {
            model.addAttribute("newbMessageZalo", new NewbMessageZalo());
        } catch (Exception ex) {
            LOG.debug("", ex);
        }
        LOG.debug("END::create.html");
        return "/message/create";
    }

    @RequestMapping(value = "/create.html", method = {RequestMethod.POST})
    public String create( Model model, @ModelAttribute("newbMessageZalo") NewbMessageZalo newbMessageZalo){
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug("BEGIN::create.html");
        try {
            newbMessageZalo.setStatus(1);
            commonService.create(transId, newbMessageZalo);

            ZaloOaClient client = new ZaloOaClient();
            String access_token = "dFAKOtWHSI-OyPHiU1Dt8ftma6b-3X8-XPRrTZ4iJpkUkEqsU1y9ByM1Z65H1IPvle2dHX4yC4QtfA9DEHe40eAwkImRNb9XgVZCKoLGPcw5zUD464vWVOFj-tTa1N9LugBLLquhDLxhpA1APqaGUOdUZJefO0qghSYG8n5vCXE8pvSzF7090DdAcJHXLdiPy_pQAX01GnIIXj8s7315F9sh_NCg67y-ahcOTpCj2M27h9Ht5XKCUwM4xqWt4tD3fgpFMY08LKwnaB1m01CcUvMGicT8HzRGqtfv37Kl";

            Map<String, Object> params = new HashMap<>();
            params.put("access_token", access_token);

            JsonObject id = new JsonObject();
            id.addProperty("user_id", newbMessageZalo.getPhone());

            JsonObject text = new JsonObject();
            text.addProperty("text", newbMessageZalo.getMessage());

            JsonObject body = new JsonObject();
            body.add("recipient", id);
            body.add("message", text);

            LOG.info(GSON.toJson(body));

            JsonObject result = client.excuteRequest("https://openapi.zalo.me/v2.0/oa/message", "POST", params, body);
            System.out.println(result);
            model.addAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.ADDNEW_SUCCESS);
            model.addAttribute("newbMessageZalo", new NewbMessageZalo());
        } catch (Exception e) {
            LOG.error("", e);
            model.addAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.ADDNEW_ERROR);
            model.addAttribute("newbMessageZalo", newbMessageZalo);
        } finally {
            LOG.debug("END::create.html");
        }
        return "redirect:index.html";
    }


}
