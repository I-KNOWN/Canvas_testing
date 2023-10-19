package com.holybible.biblereader.canvas_testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MultipleLines extends View {

    private Bitmap bitmap;
    private Canvas canvas;

    private Paint mPaint;

    public MultipleLines(Context context) {
        super(context);
        init();
    }

    public MultipleLines(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    private boolean isDrawing;
    private List<PointF> points = new ArrayList<>();

    private void touch_start(float touchX, float touchY) {
        isDrawing = true;
        points.add(new PointF(touchX, touchY));

        canvas.save();
    }

    private void touch_move(float touchX, float touchY) {
        if (!isDrawing) return;

        canvas.drawColor(Color.TRANSPARENT);

        points.add(new PointF(touchX, touchY));

        stroke(offsetPoints(-10));
        stroke(offsetPoints(-5));
        stroke(points);
        stroke(offsetPoints(5));
        stroke(offsetPoints(10));
    }

    private void touch_up() {
        isDrawing = false;
        points.clear();
        canvas.restore();
    }

    private List<PointF> offsetPoints(float val) {
        List<PointF> offsetPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            PointF point = points.get(i);
            offsetPoints.add(new PointF(point.x + val, point.y + val));
        }
        return offsetPoints;
    }

    private void stroke(List<PointF> points) {
        PointF p1 = points.get(0);
        PointF p2 = points.get(1);

        Path path = new Path();
        path.moveTo(p1.x, p1.y);

        for (int i = 1; i < points.size(); i++) {
            // we pick the point between pi+1 & pi+2 as the
            // end point and p1 as our control point
            PointF midPoint = midPointBtw(p1, p2);
            path.quadTo(p1.x, p1.y, midPoint.x, midPoint.y);
            p1 = points.get(i);
            if (i + 1 < points.size()) p2 = points.get(i + 1);
        }
        // Draw last line as a straight line while
        // we wait for the next point to be able to calculate
        // the bezier control point
        path.lineTo(p1.x, p1.y);

        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    private PointF midPointBtw(PointF p1, PointF p2) {
        return new PointF(p1.x + (p2.x - p1.x) / 2.0f, p1.y + (p2.y - p1.y) / 2.0f);
    }
}