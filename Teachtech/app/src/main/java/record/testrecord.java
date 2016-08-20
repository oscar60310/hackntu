package record;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import tech.hackntu.msp.teachtech.R;

/**
 * Created by Goofy on 2016/8/20.
 */
public class testrecord
{
    WindowManager windowManager;
    public testrecord(Activity v)
    {
        mv = v;
    }
    Activity mv;
    int c = 1;
    public void testMod()
    {
        windowManager = (WindowManager) mv.getSystemService(mv.WINDOW_SERVICE);

        final ImageView chatHead = new ImageView(mv);
        chatHead.setImageResource(R.drawable.tien);


       final  WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(chatHead, params);
        chatHead.getLayoutParams().height = 500;
        chatHead.getLayoutParams().width = 500;
        chatHead.setAlpha(50);
        chatHead.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
              //  Toast.makeText(mv,motionEvent.getX()+","+motionEvent.getY(),Toast.LENGTH_SHORT);
                Log.d("pix",motionEvent.getX()+","+motionEvent.getY());
                if (c==4)
                {
                    c =0;
                    windowManager.removeView(chatHead);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            testMod();
                        }
                    }, 2000);
                }
                else
                {
                    c++;
                }
                return false;
            }
        });

    }
}
