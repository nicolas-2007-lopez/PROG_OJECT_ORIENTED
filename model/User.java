package model;

import java.util.ArrayList;
import java.util.List;
import model.interfaces.Tuit;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private int age;
    private String profilePicture;
    private List<Tuit> history;
    private List<User> followers;
    private List<User> following;
    private List<Tuit> likedTuits;
    private String password;

    public User(String name, String email, String password, int age, String profilePicture) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.age = age;
    this.profilePicture = profilePicture;
        this.history = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.likedTuits = new ArrayList<>();
    }
       public boolean verificarPassword(String input) {
        return this.password.equals(input);
    }

   
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getProfilePicture() {
    return profilePicture;
}

    


    public Post createPost(String content, String media, String link) {
        Post post = new Post(content, media, link, this);
        history.add(post);
        return post;
    }

    public void likeTuit(Tuit tuit) {
        if (!likedTuits.contains(tuit)) {
            tuit.addLike(this);
            likedTuits.add(tuit);
        }
    }

    public void followUser(User user) {
        if (!following.contains(user) && user != this) {
            following.add(user);
            user.followers.add(this);
        }
    }

    public void unfollowUser(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.followers.remove(this);
        }
    }

    public List<Tuit> getHistory() {
        return history;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<Tuit> getLikedTuits() {
        return likedTuits;
    }


}
