package com.holybible.biblereader.canvas_testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WritingView extends View {

    Paint paint;

    MyPoint initialPoint;
    ArrayList<LineModel> allPoint;
    BitmapShader fillBMPshader;
    Path path;

    public WritingView(Context context) {
        super(context);
        init();

    }

    public WritingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(30f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
/*        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.texture);
        fillBMPshader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP);
        paint.setShader(fillBMPshader);*/
        allPoint = new ArrayList<>();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawLine(100, 100, 200, 200, paint);

/*
        int insetX = 100;
        int insetY = 40;
*/
/*        path.moveTo(0, 0); // top left
        path.lineTo(300 - insetX, 0); // top  right/inset top*//*

        path.addArc(300,200,300,500,100,0);
        path.arcTo(300,200,300,500,100, 0,true);
//        path.lineTo(300 - insetX, insetY); // inset corner
*/
/*        path.lineTo(300, insetY) ;// top right inset bottom
        path.lineTo(300, 200); // bottom right
        path.lineTo(0, 200); // bottom left*//*

        path.close();// back to the start

        canvas.drawPath(path, paint);
*/

        for(LineModel line: allPoint){
            canvas.drawLine(line.getStartpoint().x, line.getStartpoint().y, line.getEndpoint().x, line.getEndpoint().y, paint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("Whatisthepoint", "Event: "+event.getAction());

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            initialPoint = new MyPoint(event.getX(), event.getY());

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d("Whatisthepoint", "getx: " + event.getX()+"\ngety: "+event.getY());
            allPoint.add(new LineModel(initialPoint,new MyPoint(event.getX(), event.getY())));
            initialPoint = new MyPoint(event.getX(), event.getY());
            invalidate();
        }

        return true;


    }
}
