package vn.hbm.jpa;

import org.springframework.web.multipart.MultipartFile;
import vn.hbm.core.annotation.AntColumn;
import vn.hbm.core.annotation.AntTable;

import java.io.Serializable;

@AntTable(name = "newb_news", key = "id")
public class NewbNews implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private int id;
    private String iconLike;
    private String typeNews;
    private String title;
    private String image;
    private int replies;
    private int views;
    private int activity;
    private int votes;
    private int status;

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public int getId() {
        return id;
    }

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public void setId(int id) {
        this.id = id;
    }

    @AntColumn(name = "icon_like", index = 1)
    public String getIconLike() {
        return iconLike;
    }

    @AntColumn(name = "icon_like", index = 1)
    public void setIconLike(String iconLike) {
        this.iconLike = iconLike;
    }

    @AntColumn(name = "type_news", index = 2)
    public String getTypeNews() {
        return typeNews;
    }

    @AntColumn(name = "type_news", index = 2)
    public void setTypeNews(String typeNews) {
        this.typeNews = typeNews;
    }

    @AntColumn(name = "title", index = 3)
    public String getTitle() {
        return title;
    }

    @AntColumn(name = "title", index = 3)
    public void setTitle(String title) {
        this.title = title;
    }

    @AntColumn(name = "image", index = 4)
    public String getImage() {
        return image;
    }

    @AntColumn(name = "image", index = 4)
    public void setImage(String image) {
        this.image = image;
    }

    @AntColumn(name = "replies", index = 5)
    public int getReplies() {
        return replies;
    }

    @AntColumn(name = "replies", index = 5)
    public void setReplies(int replies) {
        this.replies = replies;
    }

    @AntColumn(name = "views", index = 6)
    public int getViews() {
        return views;
    }

    @AntColumn(name = "views", index = 6)
    public void setViews(int views) {
        this.views = views;
    }

    @AntColumn(name = "activity", index = 7)
    public int getActivity() {
        return activity;
    }

    @AntColumn(name = "activity", index = 7)
    public void setActivity(int activity) {
        this.activity = activity;
    }

    @AntColumn(name = "votes", index = 8)
    public int getVotes() {
        return votes;
    }

    @AntColumn(name = "votes", index = 8)
    public void setVotes(int votes) {
        this.votes = votes;
    }

    @AntColumn(name = "status", index = 9)
    public int getStatus() {
        return status;
    }

    @AntColumn(name = "status", index = 9)
    public void setStatus(int status) {
        this.status = status;
    }
}
