 import java.util.Date;
 import java.util.List;
        public class User {
        private String name;
        private int age;
        private String email;
        private String password;
        private String profilePicture;
        private List<User> following;
        private List<Post> post;
    
        public void register() {
            
        }
    
        public void login() {
           
        }
    
        public void follow(User user) {
          
        }
    
        public void unfollow(User user) {
          
        }
    
        public void receiveNotification(Notification n) {
           
        }
    }
    
    abstract class Post {
        protected String content;
        protected List<String> media;
        protected Date timestamp;
        protected User author;
        protected int likes;
    
        public void publish() {
            
        }
    
        public void like(User user) {
            
        }
    
        public abstract String getPreview();
    }
    
  
    abstract class Notification {
        protected String type; 
        protected User fromUser;
        protected Date timestamp;
    
        public void configureSettings() {
            
        }
    
        public abstract void sendNotification(User user);
    }
    

    class Feed {
        private List<Post> posts;
        private User owner;
    
        public List<Post> sortByDate() {
            
            return posts;
        }
    
        public List<Post> sortByRelevance() {
           
            return posts;
        }
    
        public void display() {
           
        }
    }
