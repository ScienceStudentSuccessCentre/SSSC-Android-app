package ghelani.kshamina.sssc_android_app.entity;

public class Mentor {

    private String id;
    private String name;
    private String url;
    private String degree;
    private String bio;
    private String imageUrl;
    private String team;

    public Mentor(String id, String name, String url, String degree, String bio, String imageUrl, String team) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.degree = degree;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
