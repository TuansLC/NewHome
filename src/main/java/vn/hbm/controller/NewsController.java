package vn.hbm.controller;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hbm.bean.NewBean;
import vn.hbm.core.common.Common;
import vn.hbm.core.common.Constants;
import vn.hbm.core.common.MessageContants;
import vn.hbm.core.service.CommonService;
import vn.hbm.jpa.NewbNews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/news")
public class NewsController {
    private final Logger LOG = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private CommonService commonService;

    //CREATE NEWS

    @RequestMapping(value = "/create.html", method = {RequestMethod.GET})
    public String indexAddNew(Locale locale, Model model) {
        LOG.debug("BEGIN::create.html");
        try {
            model.addAttribute("newbNews", new NewbNews());
        } catch (Exception ex) {
            LOG.debug("", ex);
        }
        LOG.debug("END::create.html");
        return "news/create";
    }

    @RequestMapping(value = "/create.html", method = {RequestMethod.POST})
    public String create(Locale locale, Model model,@RequestParam("file") MultipartFile file,
                         @ModelAttribute("newbNews") NewbNews newbNews,
                        // @ModelAttribute("newbNews") @Valid NewBean newbNews,
                         RedirectAttributes redirectAttributes
                         ) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug("BEGIN::create.html");


        // UPLOAD FILE
        try {
            Path path = Paths.get("uploads/");
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            newbNews.setImage(file.getOriginalFilename().toUpperCase());
            newbNews.setStatus(1);
            commonService.create(transId, newbNews);
            model.addAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.ADDNEW_SUCCESS);
            model.addAttribute("newbNews", new NewbNews());
        } catch (Exception e) {
            LOG.error("", e);
            model.addAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.ADDNEW_ERROR);
            model.addAttribute("newbNews", newbNews);
        } finally {
            LOG.debug("END::create.html");
        }
        return "redirect:index.html";
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

    // GET LIST NEW

    @RequestMapping(value = "/index.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String indexNews(HttpServletRequest request, Model model, Pageable pageable) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug(transId + "::BEGIN::index.html");
        try {
            NewbNews frm = new NewbNews();
            request.getSession().removeAttribute("formObj");
            initData(transId, model, pageable, "", "");
            model.addAttribute("formObj", frm);

        } catch (Exception ex) {
        }
        LOG.debug(transId + "::END::index.html");
        return "news/index";
    }

     // SEARCH NEWS
    @RequestMapping(value = "/search-index.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String searchCheck(HttpServletRequest request, Model model, Pageable pageable,
                              @ModelAttribute("formObj") NewbNews frm,
                              HttpServletResponse response) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug(transId + "::BEGIN::search-index.html");
        try {
            initData(transId, model, pageable, frm.getTypeNews(), frm.getTitle());
            request.getSession().setAttribute("formObj", frm);
            model.addAttribute("formObj", frm);
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        LOG.debug(transId + "::END::search-index.html");
        return "/news/index";

    }




    private void initData(String transId, Model model, Pageable pageable, String type_news, String title) {
        Page<NewbNews> pageData = null;
        Pageable _pageable = null;
        try {
            List<NewbNews> list = new ArrayList<>();
            list.addAll(getListData(transId, type_news, title));
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

    private List<NewbNews> getListData(String transId, String type_news, String title) {
        List<NewbNews> list = new ArrayList<>();
        try {
            String sql = " SELECT icon_like, type_news, title, image, replies, views, activity, votes, id, status " +
                    " FROM newb_news " +
                    " WHERE status=1 " +
                    " AND (type_news = ? OR ? = '') AND (title = ? OR ? = '') ";

            List<Object[]> lstObj = commonService.findBySQL(transId, sql, new String[]{type_news, type_news, title, title});

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

    //UPDATE NEWS

    @RequestMapping(value = "/update.html/{id}", method = {RequestMethod.GET})
    public String indexUpdate(Locale locale, Model model,
                              @PathVariable("id") Integer id) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        LOG.debug("BEGIN::update.html");
        LOG.debug("UPDATE::PARAM[id="+ id +"]");
        try {
            NewbNews newbNews = findOne(transId, Long.valueOf(id));
            model.addAttribute("newbNews", newbNews);
        } catch (Exception ex) {
            LOG.error("", ex);
        } finally {
            LOG.debug("END::update.html");
        }
        return "/news/update";
    }


    @RequestMapping(value = "/update.html", method = {RequestMethod.POST})
    public String update(Locale locale, Model model,
                         @ModelAttribute("newbNews") NewbNews updateNewbNews,
                         @RequestParam("file") MultipartFile file,
                         RedirectAttributes redirectAttributes) {
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);

        Path path = Paths.get("uploads/");
        LOG.debug("BEGIN::update.html");
        try {
            NewbNews newbNews= findOne(transId, Long.valueOf(updateNewbNews.getId()));

            if (!file.isEmpty()) {
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
                        StandardCopyOption.REPLACE_EXISTING);
                updateNewbNews.setImage(file.getOriginalFilename().toUpperCase());
            }
            commonService.update(transId, updateNewbNews, null, null, false, null);
            redirectAttributes.addFlashAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.UPDATE_SUCCESS);
            model.addAttribute("newbNews", new NewbNews());
            return "redirect:/news/index.html";
        }  catch (Exception e) {
        LOG.error("", e);
    } finally {
        LOG.debug("END::update.html");
    }
        return "/news/update";
    }


    // DELETE NEWS


    @RequestMapping(value = "/delete.html/{id}", method = {RequestMethod.GET})
        public String delete(Locale locale, Model model,
                             @PathVariable("id") Integer id,
                             Pageable pageable,
                             RedirectAttributes redirectAttributes) {
            String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
            LOG.debug("BEGIN::delete.html");
            LOG.debug("PARAM [id=" + id + "]");
            NewbNews newbNews = new NewbNews();
            try {
                newbNews = findOne(transId, Long.valueOf(id));
                if (newbNews != null) {
                    newbNews.setStatus(0);
                    commonService.update(transId, newbNews, null, null, false, null);
                    redirectAttributes.addFlashAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.DELETE_SUCCESS);
                }  else {
                    model.addAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.DELETE_ERROR);
                    redirectAttributes.addFlashAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.DELETE_ERROR);
                }  }  catch (Exception ex) {
                LOG.error("", ex);
                model.addAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.DELETE_ERROR);
                redirectAttributes.addFlashAttribute(MessageContants.Result.MESSAGE_CODE, MessageContants.Account.DELETE_ERROR);
            }
            LOG.debug("END::delete.html");
            return "redirect:/news/index.html";
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
