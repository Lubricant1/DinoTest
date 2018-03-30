package example.dinotest.entity;

public class Session {

    private String id;
    private String name;
    private String token;
    private String user;

    public Session() {
    }

    public Session(String name, String id, String token, String user) {
        this.name = name;
        this.id = id;
        this.token = token;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
