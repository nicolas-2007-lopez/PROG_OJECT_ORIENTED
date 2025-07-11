package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.interfaces.Tuit;
import java.io.Serializable;

public class Post implements Tuit, Serializable {
    private String content;
    private String media;
    private String link;
    private Date date;
    private User author;
    private List<User> likes;

    public Post(String content, String media, String link, User author) {
        this.content = (content != null) ? content : "";
        this.media = (media != null) ? media : "";
        this.link = (link != null) ? link : "";
        this.author = author;
        this.date = new Date(); // ¡esto debe estar!
        this.likes = new ArrayList<>();
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getPreview() {
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    @Override
    public int getLikes() {
        return likes.size();
    }

    @Override
    public void addLike(User user) {
        if (!likes.contains(user)) {
            likes.add(user);
        }
    }
    public String getLink() {
    return link;
}
 public String getMedia() {
        return media;
    }
}
