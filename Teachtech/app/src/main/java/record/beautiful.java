package record;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Goofy on 2016/8/20.
 */
public class beautiful {
    static int corner = 30;
    public Bitmap getLine(int wid)
    {

        Bitmap b = Bitmap.createBitmap(wid, 100, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(b);
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        p.setStrokeWidth(15);
        cv.drawLine(0, corner, wid, corner, p);
        p.setStrokeWidth(20);
        cv.drawLine(0,corner,0,0,p);
        cv.drawLine(wid,corner,wid,0,p);
        return b;
    }
}
