package dwai.yhack.captor.ui.activity;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Date;

/**
 * Created by Stefan on 11/1/2014.
 */
public class OneItem implements Comparable<OneItem>{
    private Bitmap image;
    private String text;
    private DatePosted datePosted;

    public OneItem(Bitmap image, String text, DatePosted datePosted) {
        this.image = image;
        this.text = text;
        this.datePosted = datePosted;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public DatePosted getDatePosted() {
        return datePosted;
    }

    @Override
    public int compareTo(OneItem oneItem) {
        return this.datePosted.compareTo(oneItem.datePosted);
    }
}
