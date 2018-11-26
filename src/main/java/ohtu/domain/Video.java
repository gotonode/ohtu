package ohtu.domain;

import java.util.Date;
import java.util.Objects;

public class Video extends Bookmark {

    private String url;

    public Video(int id, String title, Date addDate, String url) {
            super(id, title, addDate, 'V'); // V for video.
            this.url = url;
    }

    @Override
    public String toString() {
            return "Video{" +
                            "url='" + url + '\'' +
                            '}';
    }

    public String getUrl() {
            return url;
    }

    public void setUrl(String url) {
            this.url = url;
    }

    @Override
    public boolean equals(Object object) {
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }

        return this.getUrl().equals(((Video)object).getUrl());
    }

    @Override
    public int hashCode() {
        int hash = 9;
        hash = 37 * hash + Objects.hashCode(this.url);
        return hash;
    }
}
