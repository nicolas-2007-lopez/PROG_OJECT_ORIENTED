package model.interfaces;

import java.util.Date;
import model.User;

public interface Tuit {
    User getAuthor();
    String getContent();
    Date getDate();
    String getPreview();
    int getLikes();
    void addLike(User user);
}