package tech.acodesigner.dto;

/**
 * Created by 77239 on 2017/2/11/0011.
 */
public class ArticleLiteDto {

    private int id;
    private String title;
    private String pubDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
