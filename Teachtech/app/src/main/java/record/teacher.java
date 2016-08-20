package record;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
    int screen_x,screen_y;
    beautiful bf;
    public teacher(JSONObject booko,Activity m)
    {
        book = booko;
        main = m;
        DisplayMetrics metrics = new DisplayMetrics();
        main.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_x = metrics.widthPixels;
        screen_y = metrics.heightPixels;
        bf = new beautiful();
    }
    WindowManager windowManager;
    ImageView arrow,next_btn;
    WindowManager.LayoutParams params;
    public void start_class()
    {

        windowManager = (WindowManager) main.getSystemService(main.WINDOW_SERVICE);

        arrow = new ImageView(main);
        arrow.setImageResource(R.drawable.arrow);
       // arrow.setRotation(45);

      //  arrow.setBackground(main.getDrawable(R.color.colorPrimary));
         params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(arrow, params);
    //    arrow.setVisibility(View.GONE);
        currect_step = 0;
        nextstep();
       // arrow.getLayoutParams().width = 50;
       // arrow.getLayoutParams().height = 50;


        // 浮動按鈕
        next_btn = new ImageView(main);
        next_btn.setImageResource(R.drawable.next_btn);
        WindowManager.LayoutParams btn_params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        btn_params.gravity = Gravity.TOP | Gravity.LEFT;
        btn_params.x = screen_x -200 ;
        btn_params.y = screen_y -300 ;

        windowManager.addView(next_btn, btn_params);
       // next_btn.setBackground(main.getDrawable(R.color.colorPrimary));
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextstep();
            }
        });

        try {
            //開啟APP
            Intent intent = main.getPackageManager().getLaunchIntentForPackage(book.get("package") + "");
            main.startActivity(intent);
        }
        catch (Exception e)
        {
            Log.d("error",e.toString());
        }
    }
    int currect_step;
    void nextstep()
    {
        windowManager.removeView(arrow);
        try
        {
            JSONArray books = (JSONArray)book.get("step");
            if(currect_step >= books.length()) return;
            else {
                position p = getposition((JSONObject) (books.get(currect_step)));
                arrow_appear(p);
                currect_step++;
            }
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
          //  fy = (Double)area.get("from_y");
            ty = (Double)area.get("to_y");
            position p = new position();
            p.x = Float.parseFloat(Double.toString((fx+tx)/2)) -30;
            p.y = Float.parseFloat(Double.toString(ty)) +15;
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

        params.x = Math.round(p.x);
        params.y = Math.round(p.y);
        windowManager.addView(arrow, params);
       // arrow.setImageBitmap(bf.getLine(100).b);
      //  arrow.setX(p.x);
     //   arrow.setY(p.y);
      //  arrow.setVisibility(View.VISIBLE);

    }
    public void destory()
    {

        windowManager.removeView(arrow);
        windowManager.removeView(next_btn);
    }
}
class position {
    public float x = 0;
    public float y = 0;
}
