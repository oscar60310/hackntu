package record;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

/**
 * Created by Goofy on 2016/8/20.
 */
public class smartClassroom {
    JSONObject book;
    Handler timer;
    Boolean keep = true;
    Activity main;
    Bitmap b1,b2;
    public smartClassroom(JSONObject b, Activity m)
    {
        main = m;
        book = b;
        timer= new Handler();
    }
    public void start_checking()
    {

        check();
    }
    public void check()
    {
        if(!keep)return;
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                check();
            }
        }, 500);
        //Log.d("timer","check");

        if(b1==null)
        {
            b1 = cap();
        }
        else
        {
            b2 = cap();
            viewChange(b1,b2);
            b1 = b2;
        }

    }
    public void stop()
    {
        keep = false;
    }
    Bitmap cap()
    {
        try
        {
            View view = main.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap b1 = view.getDrawingCache();

            //得到狀態列高度
            Rect frame = new Rect();
            main.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
         //   System.out.println(statusBarHeight);

            //得到螢幕長和高　
            int width = main. getWindowManager().getDefaultDisplay().getWidth();
            int height =main. getWindowManager().getDefaultDisplay().getHeight();
            //去掉標題列
            //Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
            Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
            view.destroyDrawingCache();
            return b;

        }
        catch(Exception e)
        {
            Log.d("f",e.toString());
            return null;
        }

    }
    boolean viewChange(Bitmap i1, Bitmap i2)
    {
        int range_x = i1.getWidth()/10;
        int range_y = i1.getHeight()/10;
        Double same = 0.0 ;
        Double dif = 0.0;
        for(int x = 0 ; x < i1.getWidth() ; x += range_x)
        {
            for(int y = 0 ; y<i1.getHeight();y += range_y)
            {
               if( i1.getPixel(x,y)==i2.getPixel(x,y))
                   same ++;
                else
                   dif++;
            }
        }


        Log.d("diff",Double.toString(same/(same+dif)));
        return true;
    }
}
