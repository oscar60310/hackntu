package record;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import tech.hackntu.msp.teachtech.R;

/**
 * Created by Goofy on 2016/8/20.
 */
public class teacher
{
    private JSONObject book;
    private  Activity main;
    public teacher(JSONObject booko,Activity m)
    {
        book = booko;
        main = m;
    }
    WindowManager windowManager;
    ImageView arrow;
    WindowManager.LayoutParams params;
    public void start_class()
    {
        windowManager = (WindowManager) main.getSystemService(main.WINDOW_SERVICE);

        arrow = new ImageView(main);
        arrow.setImageResource(R.drawable.arrow);
       // arrow.setRotation(45);


         params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(arrow, params);
    //    arrow.setVisibility(View.GONE);
      //  nextstep();
       // arrow.getLayoutParams().width = 50;
       // arrow.getLayoutParams().height = 50;

    }
    void nextstep()
    {
        try
        {
            position p = getposition((JSONObject)((JSONArray)book.get("step")).get(0));
            arrow_appear(p);
        }
        catch (Exception e){
            Log.d("error",e.toString());
        }

    }

    position getposition(JSONObject stepData)
    {
        try
        {
            Double fx,tx,fy,ty;
            JSONObject area = (JSONObject)stepData.get("area");
            fx = (Double)area.get("from_x");
            tx = (Double)area.get("to_x");
            fy = (Double)area.get("from_y");
            ty = (Double)area.get("to_y");
            position p = new position();
            p.x = Float.parseFloat(Double.toString((fx+tx)/2));
            p.y = Float.parseFloat(Double.toString((fy+ty)/2));
            return p;

        }
        catch (Exception e)
        {
            Log.d("error",e.toString());
            return null;
        }


    }
    private void arrow_appear(position p)
    {
       // params.x = Math.round(p.x);
      //  params.y = Math.round(p.y);
       // windowManager.addView(arrow, params);
        arrow.setX(p.x);
        arrow.setY(p.y);
        arrow.setVisibility(View.VISIBLE);

    }
}
class position {
    public float x = 0;
    public float y = 0;
}
