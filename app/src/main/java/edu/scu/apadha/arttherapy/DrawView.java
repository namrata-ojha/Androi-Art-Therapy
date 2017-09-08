package edu.scu.apadha.arttherapy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DrawView extends View {
    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;
    private boolean erase=false;
    int currColor;

    public DrawView(Context context, AttributeSet attrSet) {
        super(context, attrSet);

        path = new Path();

        paint = new Paint();
        paint.setColor(Color.BLACK);
        currColor = paint.getColor();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

        setDrawingCacheEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                this.canvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.bitmap, 0, 0, this.paint);
        canvas.drawPath(this.path, this.paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
    }

    public void setColor(int newColor) {
        if ((currColor != newColor) && (!erase)) {
            invalidate();
            paint.setColor(newColor);
            currColor = newColor;
        }
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setErase(boolean isErase){
        erase=isErase;
        if(erase) {
            paint.setStrokeWidth(30);
            paint.setColor(Color.WHITE);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        }
        else {
            paint.setColor(currColor);
            paint.setStrokeWidth(10);
            paint.setXfermode(null);
        }
    }

    /*public void saveDrawing() {
        File drawingFile = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            drawingFile = File.createTempFile("ART_THERAPY_" + timeStamp, ".jpeg", dir);
            boolean success = getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(drawingFile));
            if(success) {
                Toast.makeText(getContext(), "Drawing save!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
