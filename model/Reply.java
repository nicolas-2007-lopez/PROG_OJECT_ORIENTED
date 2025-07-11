package model;

import model.interfaces.Tuit;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class Reply implements Tuit, Serializable {
    private String content;
    private Date date;
    private User author;
    private Tuit replyTo;
    private List<User> likes;

    public Reply(String content, User author, Tuit replyTo) {
        this.content = content;
        this.author = author;
        this.replyTo = replyTo;
        this.date = new Date();
        this.likes = new ArrayList<>();
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public String getContent() {
        return "(respuesta) " + content;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getPreview() {
        return getContent().length() > 50 ? getContent().substring(0, 50) + "..." : getContent();
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

    public Tuit getReplyTo() {
        return replyTo;
    }
}
