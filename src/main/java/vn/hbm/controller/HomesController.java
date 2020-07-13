package vn.hbm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hbm.core.common.Common;
import vn.hbm.core.common.Constants;
import vn.hbm.core.service.CommonService;
import vn.hbm.jpa.NewbNews;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/home")
public class HomesController {
    private final Logger LOG = LoggerFactory.getLogger(HomesController.class);

    @Autowired
    private CommonService commonService;


    // GET LIST NEW SHOW
    @RequestMapping(value = "/index.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String indexNews(HttpServletRequest request, Model model, Pageable pageable) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug(transId + "::BEGIN::index.html");
        try {
            NewbNews frm = new NewbNews();
            request.getSession().removeAttribute("formObj");
            initData(transId, model, pageable);
            model.addAttribute("formObj", frm);

        } catch (Exception ex) {
        }
        LOG.debug(transId + "::END::index.html");
        return "home/index";
    }

    // GET IMAGE TO FILE
    @RequestMapping(value = "/getimage/{image}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("image") String image) {
        if (!image.equals("")|| image != null) {
            Path fileName = Paths.get("uploads", image);
            try {
                byte[] buffer = Files.readAllBytes(fileName);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/png"))
                        .body(byteArrayResource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private void initData(String transId, Model model, Pageable pageable) {
        Page<NewbNews> pageData = null;
        Pageable _pageable = null;
        try {
            List<NewbNews> list = new ArrayList<>();
            list.addAll(getListData(transId));
            //check đk nếu pagesize null thì mặc định lấy 20 bản ghi.

            _pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize() <= 0 ? 20
                    : pageable.getPageSize());

//            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize() <= 0 ? 20
//                    : pageable.getPageSize(), Sort.Order.asc("view"));

            //Thuc hien phan trang cho danh sach
            int fromRecord = 0;
            int toRecord = 0;

            fromRecord = _pageable.getPageNumber() * pageable.getPageSize();
            toRecord = (_pageable.getPageNumber() + 1) * pageable.getPageSize();
            if (toRecord > list.size()) {
                toRecord = list.size();
            }

            List<NewbNews> lstResult = null;
            if (list != null && !list.isEmpty()) {
                lstResult = list.subList(fromRecord, toRecord);
            } else {
                lstResult = new ArrayList<>();
            }
//            Collections.reverse(lstResult);
            pageData = new PageImpl<>(lstResult, _pageable, list.size());
        } catch (Exception ex) {
            pageData = new PageImpl<>(new ArrayList<NewbNews>(), _pageable, 0);
        } finally {
            model.addAttribute("page", pageData);
        }
    }

    private List<NewbNews> getListData(String transId) {
        List<NewbNews> list = new ArrayList<>();
        try {
            String sql = " SELECT icon_like, type_news, title, image, replies, views, activity, votes, id, status " +
                    " FROM newb_news " +
                    " WHERE status=1 ";

            List<Object[]> lstObj = commonService.findBySQL(transId, sql, new String[]{});

            if (lstObj != null && !lstObj.isEmpty()) {
                lstObj.stream().forEach(item -> {
                    NewbNews bean = new NewbNews();
                    bean.setIconLike(String.valueOf(item[0]));
                    bean.setTypeNews(String.valueOf(item[1]));
                    bean.setTitle(String.valueOf(item[2]));
                    bean.setImage(String.valueOf(item[3]));
                    bean.setReplies(Integer.valueOf(item[4].toString()));
                    bean.setViews(Integer.valueOf(item[5].toString()));
                    bean.setActivity(Integer.valueOf(item[6].toString()));
                    bean.setVotes(Integer.valueOf(item[7].toString()));
                    bean.setId(Integer.valueOf(item[8].toString()));
                    bean.setStatus(Integer.valueOf(item[9].toString()));
                    list.add(bean);
                });
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return list;
    }

    // Dùng ajax call => load 10 bản ghi
    @RequestMapping(value = "/ajaxtest.html", method = RequestMethod.GET)
    public String getNews(HttpServletRequest request, Model model, Pageable pageable, String page) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug(transId + "::BEGIN::post.html");
        try {
            NewbNews frm = new NewbNews();
            request.getSession().removeAttribute("formObj");
//            pageable = new PageRequest(Integer.parseInt(page), 10, null);
            pageable = PageRequest.of(Integer.parseInt(page), 10);
            initData(transId, model, pageable);
            model.addAttribute("formObj", frm);
        } catch (Exception ex) {
        }
        LOG.debug(transId + "::END::post.html");
        return "/home/post";
    }


    // GET DETAIL

    @RequestMapping(value = "/detail.html/{id}", method = {RequestMethod.GET})
    public String detail(Locale locale, Model model,
                         @PathVariable("id") Long id,
                         Pageable pageable,
                         RedirectAttributes redirectAttributes) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug("BEGIN::detail.html");
        LOG.debug("PARAM [id=" + id + "]");
        NewbNews newbNews = new NewbNews();
        try {
            newbNews = findOne(transId, id);
            if (newbNews != null) {
                commonService.findById(transId, newbNews, id);
                model.addAttribute("newbNews", newbNews);
            } else {
                System.out.println("Error");
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        LOG.debug("END::detail.html");
        return "/home/detail";
    }

    public NewbNews findOne(String transId, Long id) {
        List<NewbNews> list = new ArrayList<>();
        try {
            String sql = " SELECT * FROM newb_news WHERE id = ? ";
            List<Object[]> listObj = commonService.findBySQL(transId, sql, new Long[]{id});
            if (!Common.isEmpty(listObj)) {
                listObj.stream().forEach(item -> {
                    NewbNews newbNews = new NewbNews();
                    newbNews.setId(Integer.valueOf(String.valueOf(item[0])));
                    newbNews.setIconLike(String.valueOf(item[1]));
                    newbNews.setTypeNews(String.valueOf(item[2]));
                    newbNews.setTitle(String.valueOf(item[3]));
                    newbNews.setImage(String.valueOf(item[4]));
                    newbNews.setReplies(Integer.valueOf(String.valueOf(item[5])));
                    newbNews.setViews(Integer.valueOf(String.valueOf(item[6])));
                    newbNews.setActivity(Integer.valueOf(String.valueOf(item[7])));
                    newbNews.setVotes(Integer.valueOf(String.valueOf(item[8])));
                    newbNews.setStatus(Integer.valueOf(String.valueOf(item[9])));
                    list.add(newbNews);
                });
                return list.get(0);
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }
}
