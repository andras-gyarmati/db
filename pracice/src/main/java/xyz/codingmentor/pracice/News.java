package xyz.codingmentor.pracice;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 *
 * @author brianelete
 */
@Entity
@IdClass(NewsId.class)
public class News implements Serializable {

    @Id
    private String title;
    @Id
    private String language;
    private NewsId id;
    private String content;

    public News() {
    }

    public NewsId getId() {
        return id;
    }

    public void setId(NewsId id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
