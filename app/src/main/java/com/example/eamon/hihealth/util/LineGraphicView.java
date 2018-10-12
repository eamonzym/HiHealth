package com.example.eamon.hihealth.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/5/5.
 */

public class LineGraphicView extends View {
    /**
     *  公共部分
     */
    private static  final int CIRCLE_SIZE =10;

    private static enum Linestyle {
        line, Curve
    }

    private Context mContext;
    private Paint mPaint;
    private Resources res;
    private DisplayMetrics dm;

    /**
     * data
     */

    private Linestyle mStyle = Linestyle.Curve;

    private int canvasHeight;
    private int canvasWidth;
    private int bheight = 0;
    private int blwidh;
    private boolean isMeasure = true;

    /**
     *  Y轴最大值
     */
    private int maxValue;

    /**
     * Y轴间距值
     */
    private int averageValue;
    private int marginTop = 5;
    private int marginBottom = 10;

    /**
     * 曲线上总点数
     */
    private Point[] mPoints;

    /**
     * 纵坐标值
     */
    private ArrayList<Double> yRawData;

    /**
     * 横坐标值
     */

    private ArrayList<String> xRawDatas;
    // 记录每个X的值
    private ArrayList<Integer> xList = new ArrayList<Integer>();
    private int spacingHeight;

    private int lineColor;

    public LineGraphicView(Context context) {
        this(context,null);
    }
    public LineGraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        this.res = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (isMeasure) {
            this.canvasHeight = getHeight();
            this.canvasWidth = getWidth();
            if (bheight == 0)
                bheight = (int) (canvasHeight - marginBottom);
            blwidh = dip2px(30);
            isMeasure = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(lineColor);

        // 画直线 (纵向)
        drawAllXline(canvas);
        drawAllYline(canvas);

        // 点的操作设置
        mPoints = getPoints();

        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(dip2px(2.5f));
        mPaint.setStyle(Paint.Style.STROKE);

        if (mStyle == Linestyle.Curve) {
            drawScrollLine(canvas);
        } else {
            drawLine(canvas);
        }

        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mPoints.length; i++) {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y,
                    CIRCLE_SIZE / 2, mPaint);
        }

    }

    /**
     * 画所有横向表格，包括X轴
     */
    private void drawAllXline(Canvas canvas) {
        for (int i = 0; i < spacingHeight + 1; i++) {

        }
    }

    /**
     * 画所有纵向表格，包括Y轴
     */
    private void drawAllYline(Canvas canvas) {
        for (int i = 0; i < yRawData.size(); i++) {
            xList.add(blwidh + (canvasWidth - blwidh) / yRawData.size() * i);
        }
    }

    private void drawScrollLine(Canvas canvas) {
        Point startp = new Point();
        Point endp = new Point();

        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i+1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;

            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, mPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        Point startp = new Point();
        Point endp = new Point();

        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
        }
    }

    private void drawText(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(12));
        p.setColor(lineColor);
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, x, y, p);
    }

    private Point[] getPoints() {
        Point[] points = new Point[yRawData.size()];
        for (int i = 0; i < yRawData.size(); i++) {
            int ph = bheight - (int) (bheight * (yRawData.get(i) / maxValue));

            points[i] = new Point(xList.get(i), ph + marginTop);

        }
        return points;
    }

    public void setData(ArrayList<Double> yRawData, ArrayList<String> xRawData, int maxValue, int averageValue) {
        this.maxValue = maxValue;
        this.averageValue = averageValue;
        this.mPoints = new Point[yRawData.size()];
        this.xRawDatas = xRawData;
        this.yRawData = yRawData;
        this.spacingHeight = maxValue / averageValue;
    }

    public void setTotalvalue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setPjvalue(int averageValue) {
        this.averageValue = averageValue;
    }

    public void setMargint(int marginTop) {
        this.marginTop = marginTop;
    }

    public void setMarginb(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setMstyle(Linestyle mStyle) {
        this.mStyle = mStyle;
    }

    public void setBheight(int bheight) {
        this.bheight = bheight;
    }

    /**
     *  根据手机的分辨率从 dp 的单位 转换成px
     */
    private int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

}
