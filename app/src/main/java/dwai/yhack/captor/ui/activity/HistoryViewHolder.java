package dwai.yhack.captor.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dwai.yhack.captor.R;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Stefan on 11/1/2014.
 */
@LayoutId(R.layout.subtitle_item)
public class HistoryViewHolder extends ItemViewHolder<OneItem>{

    @ViewId(R.id.mainText)
    TextView mainText;

    @ViewId(R.id.dateText)
    TextView dateText;

    @ViewId(R.id.imageItem)
    ImageView imageItem;

    public HistoryViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(OneItem oneItem, PositionInfo positionInfo) {
        imageItem.setImageBitmap(oneItem.getImage());
//        imageItem.setImageDrawable(getContext().getResources().getDrawable(android.R.color.white));
        dateText.setText(oneItem.getDatePosted().toString());
        mainText.setText(oneItem.getText());


    }

}
