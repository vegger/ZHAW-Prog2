package ch.zhaw.prog2.io.picturedb;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static ch.zhaw.prog2.io.picturedb.FilePictureDatasource.DATE_FORMAT;
import static ch.zhaw.prog2.io.picturedb.FilePictureDatasource.DELIMITER;

public class Picture extends Record {

    private final URL url;
    private final Date date;
    private final String title;
    private final float longitude;
    private final float latitude;
    public static final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    public Picture(URL url, Date date, String title, float longitude, float latitude) {
        this.url = url;
        this.date = date;
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Picture(URL url, String title) {
        this(url, new Date(), title, 0, 0);
    }

    protected Picture(long id, URL url, Date date, String title, float longitude, float latitude) {
        this(url,date, title, longitude, latitude);
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return Float.compare(picture.longitude, longitude) == 0 &&
            Float.compare(picture.latitude, latitude) == 0 &&
            url.equals(picture.url) &&
            date.equals(picture.date) &&
            title.equals(picture.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, date, title, longitude, latitude);
    }

    @Override
    public String toString() {
        return "Picture{" +
            "id=" + id +
            ", url=" + url +
            ", date=" + date +
            ", title='" + title + '\'' +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            '}';
    }

    public String toCSV() {
        return String.join(DELIMITER,
            String.valueOf(id),
            df.format(date),
            String.valueOf(longitude),
            String.valueOf(latitude),
            title,
            url.toExternalForm()
        );
    }

}
