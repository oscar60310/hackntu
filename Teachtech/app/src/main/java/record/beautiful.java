package record;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Goofy on 2016/8/20.
 */
public class beautiful {
    public Bitmap getLine(int wid)
    {
        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(b);
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        cv.drawLine(60, 40, 100, 40, p);
        return b;
    }
}
