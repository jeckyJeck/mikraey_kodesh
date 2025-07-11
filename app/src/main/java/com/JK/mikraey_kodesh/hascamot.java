package com.JK.mikraey_kodesh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import android.widget.Toast;

public class hascamot extends AppCompatActivity
{
    ZoomableImageView imageView ;
    RelativeLayout mainLayout;
    SharedPreferences mPrefs;

    ImageButton nextButton;
    ImageButton previousButton;
    Context context;
    Resources resources;
    int imageID;
    int imageDrawableID;
    dataManage dm;

    public hascamot(){
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("MyPrefsFile", 0);
        if (mPrefs.getInt("BlackBackground", 0) == 1)
            setTheme(R.style.AppThemeDark);
        context = this;
        dm = new dataManage();
        setContentView(R.layout.hascamot);
        imageView = findViewById(R.id.IMAGEID);
        mainLayout = findViewById(R.id.mainlayout);
        nextButton = findViewById(R.id.nextBt);
        previousButton = findViewById(R.id.previousBt);
        previousButton.setVisibility(View.GONE);
        resources = getResources();
        imageID = 0;
        imageDrawableID = resources.getIdentifier(dm.getHascamaName(0), "drawable", context.getPackageName());
        imageView.setImageBitmap(BitmapFactory.decodeResource(resources, imageDrawableID));

        //Toast.makeText(context, "החלק ימינה ושמאלה לראות הסכמות נוספות", Toast.LENGTH_SHORT).show();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextImage();
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreviousImage();
            }
        });
        mainLayout.setOnTouchListener(new swipeListener(context){

            @Override
            public void onSwipeRight() {
                showNextImage();
                Toast.makeText(context, "קרה משהו", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSwipeLeft() {
                showPreviousImage();
                Toast.makeText(context, "קרה משהו", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_back_icon, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.settings_back)
            finish();
        return true;
    }
    //    public final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//        private static final int SWIPE_THRESHOLD = 100;
//        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            boolean result = false;
//            try {
//                float diffY = e2.getY() - e1.getY();
//                float diffX = e2.getX() - e1.getX();
//                if (Math.abs(diffX) > Math.abs(diffY)) {
//                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffX > 0) {
//                            showNextImage();
//                        } else {
//                            showPreviousImage();
//                        }
//                        result = true;
//                    }
//                }
//                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                    if (diffY > 0) {
//                        onSwipeBottom();
//                    } else {
//                        onSwipeTop();
//                    }
//                    result = true;
//                }
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            return result;
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showNextImage(){
        if (imageID == 0)
            previousButton.setVisibility(View.VISIBLE);
        if (imageID <dataManage.HASCAMOT_NUMBER)
            imageID++;
        if (imageID == dataManage.HASCAMOT_NUMBER)
            nextButton.setVisibility(View.GONE);

        imageDrawableID = resources.getIdentifier(dm.getHascamaName(imageID), "drawable", context.getPackageName());
        imageView.setImageBitmap(BitmapFactory.decodeResource(resources, imageDrawableID));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showPreviousImage(){
        if (imageID == dataManage.HASCAMOT_NUMBER)
            nextButton.setVisibility(View.VISIBLE);
        if (imageID > 0)
            imageID--;
        if (imageID == 0)
            previousButton.setVisibility(View.GONE);
        imageDrawableID = resources.getIdentifier(dm.getHascamaName(imageID), "drawable", context.getPackageName());
        imageView.setImageBitmap(BitmapFactory.decodeResource(resources, imageDrawableID));
    }
    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }


    ///***********************************
//    public class ZoomableImageView extends android.support.v7.widget.AppCompatImageView
//    {
//        Matrix matrix = new Matrix();
//
//        static final int NONE = 0;
//        static final int DRAG = 1;
//        static final int ZOOM = 2;
//        static final int CLICK = 3;
//        int mode = NONE;
//
//        hascamot hascamot;
//
//        PointF last = new PointF();
//        PointF start = new PointF();
//        float minScale = 1f;
//        float maxScale = 4f;
//        float[] m;
//
//        float redundantXSpace, redundantYSpace;
//        float width, height;
//        float saveScale = 1f;
//        float right, bottom, origWidth, origHeight, bmWidth, bmHeight;
//
//        private final GestureDetector gestureDetector;
//        ScaleGestureDetector mScaleDetector;
//        Context context;
//
//        public ZoomableImageView(Context context, AttributeSet attr)
//        {
//            super(context, attr);
//
//            super.setClickable(true);
//            this.context = context;
//            hascamot = new hascamot();
//            gestureDetector = new GestureDetector(context, new GestureListener());
//            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
//            matrix.setTranslate(1f, 1f);
//            m = new float[9];
//            setImageMatrix(matrix);
//            setScaleType(ScaleType.MATRIX);
//
//            setOnTouchListener(new OnTouchListener()
//            {
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event)
//                {
//                    mScaleDetector.onTouchEvent(event);
//                    gestureDetector.onTouchEvent(event);
//
//                    matrix.getValues(m);
//                    float x = m[Matrix.MTRANS_X];
//                    float y = m[Matrix.MTRANS_Y];
//                    PointF curr = new PointF(event.getX(), event.getY());
//
//                    switch (event.getAction())
//                    {
//                        //when one finger is touching
//                        //set the mode to DRAG
//                        case MotionEvent.ACTION_DOWN:
//                            last.set(event.getX(), event.getY());
//                            start.set(last);
//                            mode = DRAG;
//                            break;
//                        //when two fingers are touching
//                        //set the mode to ZOOM
//                        case MotionEvent.ACTION_POINTER_DOWN:
//                            last.set(event.getX(), event.getY());
//                            start.set(last);
//                            mode = ZOOM;
//                            break;
//                        //when a finger moves
//                        //If mode is applicable move image
//                        case MotionEvent.ACTION_MOVE:
//                            //if the mode is ZOOM or
//                            //if the mode is DRAG and already zoomed
//                            if (mode == ZOOM || (mode == DRAG && saveScale > minScale))
//                            {
//                                float deltaX = curr.x - last.x;// x difference
//                                float deltaY = curr.y - last.y;// y difference
//                                float scaleWidth = Math.round(origWidth * saveScale);// width after applying current scale
//                                float scaleHeight = Math.round(origHeight * saveScale);// height after applying current scale
//                                //if scaleWidth is smaller than the views width
//                                //in other words if the image width fits in the view
//                                //limit left and right movement
//                                if (scaleWidth < width)
//                                {
//                                    deltaX = 0;
//                                    if (y + deltaY > 0)
//                                        deltaY = -y;
//                                    else if (y + deltaY < -bottom)
//                                        deltaY = -(y + bottom);
//                                }
//                                //if scaleHeight is smaller than the views height
//                                //in other words if the image height fits in the view
//                                //limit up and down movement
//                                else if (scaleHeight < height)
//                                {
//                                    deltaY = 0;
//                                    if (x + deltaX > 0)
//                                        deltaX = -x;
//                                    else if (x + deltaX < -right)
//                                        deltaX = -(x + right);
//                                }
//                                //if the image doesnt fit in the width or height
//                                //limit both up and down and left and right
//                                else
//                                {
//                                    if (x + deltaX > 0)
//                                        deltaX = -x;
//                                    else if (x + deltaX < -right)
//                                        deltaX = -(x + right);
//
//                                    if (y + deltaY > 0)
//                                        deltaY = -y;
//                                    else if (y + deltaY < -bottom)
//                                        deltaY = -(y + bottom);
//                                }
//                                //move the image with the matrix
//                                matrix.postTranslate(deltaX, deltaY);
//                                //set the last touch location to the current
//                                last.set(curr.x, curr.y);
//                            }
//                            break;
//                        //first finger is lifted
//                        case MotionEvent.ACTION_UP:
//                            mode = NONE;
//                            int xDiff = (int) Math.abs(curr.x - start.x);
//                            int yDiff = (int) Math.abs(curr.y - start.y);
//                            if (xDiff < CLICK && yDiff < CLICK)
//                                performClick();
//                            break;
//                        // second finger is lifted
//                        case MotionEvent.ACTION_POINTER_UP:
//                            mode = NONE;
//                            break;
//                    }
//                    setImageMatrix(matrix);
//                    invalidate();
//                    return true;
//                }
//
//            });
//        }
//
//
//
//
//        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//            private static final int SWIPE_THRESHOLD = 100;
//            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//            @Override
//            public boolean onDown(MotionEvent e) {
//                return true;
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                boolean result = false;
//                try {
//                    float diffY = e2.getY() - e1.getY();
//                    float diffX = e2.getX() - e1.getX();
//                    if (Math.abs(diffX) > Math.abs(diffY)) {
//                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//                            if (diffX > 0) {
//                                onSwipeRight();
//                            } else {
//                                onSwipeLeft();
//                            }
//                            result = true;
//                        }
//                    }
//                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffY > 0) {
//                            onSwipeBottom();
//                        } else {
//                            onSwipeTop();
//                        }
//                        result = true;
//                    }
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//                return result;
//            }
//        }
//
//        @Override
//        public void setImageBitmap(Bitmap bm)
//        {
//            super.setImageBitmap(bm);
//            bmWidth = bm.getWidth();
//            bmHeight = bm.getHeight();
//        }
//
//        public void setMaxZoom(float x)
//        {
//            maxScale = x;
//        }
//
//        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
//        {
//
//            @Override
//            public boolean onScaleBegin(ScaleGestureDetector detector)
//            {
//                mode = ZOOM;
//                return true;
//            }
//
//            @Override
//            public boolean onScale(ScaleGestureDetector detector)
//            {
//                float mScaleFactor = detector.getScaleFactor();
//                float origScale = saveScale;
//                saveScale *= mScaleFactor;
//                if (saveScale > maxScale)
//                {
//                    saveScale = maxScale;
//                    mScaleFactor = maxScale / origScale;
//                }
//                else if (saveScale < minScale)
//                {
//                    saveScale = minScale;
//                    mScaleFactor = minScale / origScale;
//                }
//                right = width * saveScale - width - (2 * redundantXSpace * saveScale);
//                bottom = height * saveScale - height - (2 * redundantYSpace * saveScale);
//                if (origWidth * saveScale <= width || origHeight * saveScale <= height)
//                {
//                    matrix.postScale(mScaleFactor, mScaleFactor, width / 2, height / 2);
//                    if (mScaleFactor < 1)
//                    {
//                        matrix.getValues(m);
//                        float x = m[Matrix.MTRANS_X];
//                        float y = m[Matrix.MTRANS_Y];
//                        if (mScaleFactor < 1)
//                        {
//                            if (Math.round(origWidth * saveScale) < width)
//                            {
//                                if (y < -bottom)
//                                    matrix.postTranslate(0, -(y + bottom));
//                                else if (y > 0)
//                                    matrix.postTranslate(0, -y);
//                            }
//                            else
//                            {
//                                if (x < -right)
//                                    matrix.postTranslate(-(x + right), 0);
//                                else if (x > 0)
//                                    matrix.postTranslate(-x, 0);
//                            }
//                        }
//                    }
//                }
//                else
//                {
//                    matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
//                    matrix.getValues(m);
//                    float x = m[Matrix.MTRANS_X];
//                    float y = m[Matrix.MTRANS_Y];
//                    if (mScaleFactor < 1) {
//                        if (x < -right)
//                            matrix.postTranslate(-(x + right), 0);
//                        else if (x > 0)
//                            matrix.postTranslate(-x, 0);
//                        if (y < -bottom)
//                            matrix.postTranslate(0, -(y + bottom));
//                        else if (y > 0)
//                            matrix.postTranslate(0, -y);
//                    }
//                }
//                return true;
//            }
//        }
//
//        @Override
//        protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
//        {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            width = MeasureSpec.getSize(widthMeasureSpec);
//            height = MeasureSpec.getSize(heightMeasureSpec);
//            //Fit to screen.
//            float scale;
//            float scaleX =  width / bmWidth;
//            float scaleY = height / bmHeight;
//            scale = Math.min(scaleX, scaleY);
//            matrix.setScale(scale, scale);
//            setImageMatrix(matrix);
//            saveScale = 1f;
//
//            // Center the image
//            redundantYSpace = height - (scale * bmHeight) ;
//            redundantXSpace = width - (scale * bmWidth);
//            redundantYSpace /= 2;
//            redundantXSpace /= 2;
//
//            matrix.postTranslate(redundantXSpace, redundantYSpace);
//
//            origWidth = width - 2 * redundantXSpace;
//            origHeight = height - 2 * redundantYSpace;
//            right = width * saveScale - width - (2 * redundantXSpace * saveScale);
//            bottom = height * saveScale - height - (2 * redundantYSpace * saveScale);
//            setImageMatrix(matrix);
//        }
//        public void onSwipeRight() {
//            Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                showNextImage();
//            }
//        }
//
//        public void onSwipeLeft() {
//            Toast.makeText(context, "left", Toast.LENGTH_SHORT).show();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                showPreviousImage();
//            }
//
//        }
//
//        public void onSwipeTop() {
//        }
//
//        public void onSwipeBottom() {
//        }
//    }
}
