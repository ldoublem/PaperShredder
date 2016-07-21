package com.ldoublem.PaperShredderlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lumingmin on 16/7/18.
 */

public class PaperShredderView extends View {

    private Paint mPaint;

    private RectF rectFPaper;
    private RectF rectFHost;
    private Path pathHostHead;

    private ArgbEvaluator evaluator;

    private float mPaddingLR;
    private int paperSlipCount = 12;
    private int paperPieceCount = 35;
    private List<Float> randomHighs = new ArrayList<>();
    private List<Float> randomQuadWidths = new ArrayList<>();
    private List<Float> randomQuadHighs = new ArrayList<>();
    private List<Float> randomQuadDirection = new ArrayList<>();


    private List<Float> PaperPiecerandX = new ArrayList<>();
    private List<Float> PaperPiecerandY = new ArrayList<>();
    private List<Float> PaperPiecerandAngle = new ArrayList<>();
    private List<Float> PaperPiecerandRadius = new ArrayList<>();


    private int bgColor = Color.rgb(213, 57, 59);
    private int paperColor = Color.WHITE;
    private int paperEnterColor = Color.rgb(148, 146, 148);

    private String title = "Deleting";
    boolean sherderTextShadow = true;

    private int titleColor = Color.WHITE;

    private SHREDEDTYPE mShrededType = SHREDEDTYPE.Slip;

    boolean sherderProgress = true;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShrededType(SHREDEDTYPE m) {
        this.mShrededType = m;
    }

    public void setSherderProgress(boolean show) {
        this.sherderProgress = show;
    }

    public void setTextColor(int color) {
        this.titleColor = color;
        invalidate();

    }

    public void setBgColor(int color) {
        this.bgColor = color;
        invalidate();
    }

    public void setPaperColor(int color) {
        this.paperColor = color;
        invalidate();
    }

    public void setTextShadow(boolean show) {
        this.sherderTextShadow = show;
        invalidate();
    }

    public void setPaperEnterColor(int color) {
        this.paperEnterColor = color;
    }


    public PaperShredderView(Context context) {
        this(context, null);
    }

    public PaperShredderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaperShredderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PaperShredderView);
        if (typedArray != null) {
            bgColor = typedArray.getColor(R.styleable.PaperShredderView_sherderBgColor, bgColor);
            titleColor = typedArray.getColor(R.styleable.PaperShredderView_sherderTextColor, titleColor);
            paperColor = typedArray.getColor(R.styleable.PaperShredderView_sherderPaperColor, paperColor);
            paperEnterColor = typedArray.getColor(R.styleable.PaperShredderView_sherderPaperEnterColor, paperEnterColor);
            title = typedArray.getString(R.styleable.PaperShredderView_sherderText);
            int type = typedArray.getInteger(R.styleable.PaperShredderView_sherderType, 0);

            if (type == 0) {
                mShrededType = SHREDEDTYPE.Slip;
            } else if (type == 1) {
                mShrededType = SHREDEDTYPE.Piece;
            } else {
                mShrededType = SHREDEDTYPE.Slip;

            }

            if (title == null) {
                title = "Deleting";
            }
            sherderProgress = typedArray.getBoolean(R.styleable.PaperShredderView_sherderProgress, true);
            sherderTextShadow = typedArray.getBoolean(R.styleable.PaperShredderView_sherderTextShadow, sherderTextShadow);
            typedArray.recycle();
        }
        initPaint();
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        rectFPaper = new RectF();
        rectFHost = new RectF();
        pathHostHead = new Path();
        evaluator = new ArgbEvaluator();


    }

    private void drawPaper(Canvas canvas) {
        mPaint.setColor(paperColor);
        rectFPaper.top = 0 + mAnimatedValue * getMeasuredHeight() / 3f * 0.8f;
        rectFPaper.bottom = getMeasuredHeight() / 3f + mAnimatedValue * getMeasuredHeight() / 3f * 0.8f;
        rectFPaper.left = mPaddingLR;
        rectFPaper.right = getMeasuredWidth() - mPaddingLR;
        canvas.drawRect(rectFPaper, mPaint);

    }


    private void drawHost(Canvas canvas) {
        rectFHost.top = getMeasuredHeight() / 2f - getMeasuredHeight() / 3f / 2f - 10;
        rectFHost.bottom = getMeasuredHeight() / 2f + getMeasuredHeight() / 3f / 2f;
        rectFHost.left = 0;
        rectFHost.right = getMeasuredWidth();
        mPaint.setColor(bgColor);

        canvas.drawRoundRect(rectFHost, mPaddingLR / 2, mPaddingLR / 2, mPaint);
        mPaint.setColor(Color.argb(80, 50, 50, 50));//shadow

        canvas.drawRoundRect(rectFHost, mPaddingLR / 2, mPaddingLR / 2, mPaint);

        rectFHost.bottom = rectFHost.bottom - mPaddingLR / 3;
        mPaint.setColor(bgColor);
        canvas.drawRoundRect(rectFHost, mPaddingLR / 3, mPaddingLR / 3, mPaint);


        pathHostHead.reset();

        pathHostHead.moveTo(rectFHost.left, rectFHost.top);
        pathHostHead.lineTo(rectFHost.right, rectFHost.top);
        pathHostHead.lineTo(rectFHost.right, rectFHost.top + mPaddingLR / 3 / 2);


        pathHostHead.quadTo(
                rectFHost.right,
                rectFHost.top + mPaddingLR / 3,
                rectFHost.right - mPaddingLR / 3,
                rectFHost.top + mPaddingLR / 3


        );

        pathHostHead.lineTo(
                rectFHost.left + mPaddingLR / 3,
                rectFHost.top + mPaddingLR / 3);

        pathHostHead.quadTo(rectFHost.left,
                rectFHost.top + mPaddingLR / 3,
                rectFHost.left,
                rectFHost.top + mPaddingLR / 3 / 2


        );
        pathHostHead.close();
        mPaint.setColor(paperEnterColor);
        canvas.drawPath(pathHostHead, mPaint);

        mPaint.setColor(titleColor);
        mPaint.setTextSize(mPaddingLR);
        if (sherderTextShadow)
            mPaint.setShadowLayer(2, 4, 4, Color.rgb(50, 50, 50));
        canvas.drawText(title, rectFHost.centerX() - getFontlength(mPaint, title) / 2, rectFHost.centerY()
                + getFontHeight(mPaint, title) / 3, mPaint);
        if (sherderProgress) {
            mPaint.setColor(Color.argb(100, 255, 255, 255));
            mPaint.setStyle(Paint.Style.STROKE);

            mPaint.setStrokeWidth(dip2px(1));
            float marginText = getFontlength(mPaint, title) / 2 + getFontHeight(mPaint, title) * 0.75f;
            canvas.drawCircle(rectFHost.centerX() - marginText, rectFHost.centerY(), getFontHeight(mPaint, title) / 2, mPaint);
            mPaint.setColor(titleColor);
            RectF rectF = new RectF(rectFHost.centerX() - getFontHeight(mPaint, title) / 2 - marginText
                    , rectFHost.centerY() - getFontHeight(mPaint, title) / 2,
                    rectFHost.centerX() + getFontHeight(mPaint, title) / 2 - marginText,
                    rectFHost.centerY() + getFontHeight(mPaint, title) / 2);
            canvas.drawArc(rectF, 360 * mAnimatedValue, 100
                    , false, mPaint);//第四个参数是否显示半径


        }
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShadowLayer(0, 0, 0, Color.WHITE);

    }

    private void drawPaperSlip(Canvas canvas) {
        Path PaperSlip = new Path();
        float paperSlipwidth = rectFPaper.width() / paperSlipCount;
        float paperSlipspace = paperSlipwidth / 7f;

        float animatedValue = mAnimatedValue;
        mPaint.setColor(paperColor);
        for (int i = 0; i < randomQuadHighs.size(); i++) {
            PaperSlip.reset();
            PaperSlip.moveTo(rectFPaper.left + i * paperSlipwidth + paperSlipspace,
                    rectFPaper.bottom
            );
            PaperSlip.lineTo(rectFPaper.left + (i + 1) * paperSlipwidth - paperSlipspace,
                    rectFPaper.bottom
            );

            float paperSlipHigh = rectFPaper.height() * 2 / 3f
                    + rectFPaper.height() / 3f * randomHighs.get(i);
            float randomQuadWidth = this.randomQuadWidths.get(i) * paperSlipspace * 2.5f * animatedValue;


            if (randomQuadDirection.get(i) > 0.5f) {
                randomQuadWidth = randomQuadWidth * -1;
            }



            float randomQuadHigh = this.randomQuadHighs.get(i) * paperSlipHigh * animatedValue;


            PaperSlip.quadTo(
                    rectFPaper.left + (i + 1) * paperSlipwidth - paperSlipspace -
                            randomQuadWidth,
                    rectFPaper.bottom + randomQuadHigh,

                    rectFPaper.left + (i + 1) * paperSlipwidth - paperSlipspace,
                    rectFPaper.bottom + paperSlipHigh
            );


            PaperSlip.lineTo(rectFPaper.left + (i) * paperSlipwidth + paperSlipspace,
                    rectFPaper.bottom + paperSlipHigh
            );
            PaperSlip.quadTo(
                    rectFPaper.left + i * paperSlipwidth + paperSlipspace -
                            randomQuadWidth,
                    rectFPaper.bottom + randomQuadHigh,


                    rectFPaper.left + i * paperSlipwidth + paperSlipspace,
                    rectFPaper.bottom
            );
            PaperSlip.close();
            canvas.drawPath(PaperSlip, mPaint);

        }

    }


    private void drawPaperPiece(Canvas canvas) {


        if (mAnimatedValue == 0)
            return;


        float paperPiecewidth = rectFPaper.width() / paperSlipCount;

        Path paperPiece = new Path();


        for (int i = 0; i < PaperPiecerandAngle.size(); i++) {

            int angle = (int) (PaperPiecerandAngle.get(i) * 360);

            angle = (int) (angle + 180 * mAnimatedValue);
            float radius = paperPiecewidth * 0.8f;
            if (PaperPiecerandRadius.get(i) < 0.3f) {
                PaperPiecerandRadius.set(i, 0.3f);

            }
            radius = radius * PaperPiecerandRadius.get(i);

            float centerX = mPaddingLR * 3 + (getMeasuredWidth() - 6 * mPaddingLR) * PaperPiecerandX.get(i);
            float centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                    + mAnimatedValue * getMeasuredHeight() / 3;
            if (PaperPiecerandRadius.get(i) >= 0.3f && PaperPiecerandRadius.get(i) <= 0.4f) {
                centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                        + mAnimatedValue * getMeasuredHeight() / 3 * 2.8f;
            } else if (PaperPiecerandRadius.get(i) > 0.4f && PaperPiecerandRadius.get(i) <= 0.5f) {
                centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                        + mAnimatedValue * getMeasuredHeight() / 3 * 2.6f;
            } else if (PaperPiecerandRadius.get(i) > 0.5f && PaperPiecerandRadius.get(i) <= 0.6f) {
                centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                        + mAnimatedValue * getMeasuredHeight() / 3 * 2.4f;
            } else if (PaperPiecerandRadius.get(i) > 0.6f && PaperPiecerandRadius.get(i) <= 0.7f) {
                centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                        + mAnimatedValue * getMeasuredHeight() / 3 * 2.2f;
            } else if (PaperPiecerandRadius.get(i) > 0.7f && PaperPiecerandRadius.get(i) <= 0.8f) {
                centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                        + mAnimatedValue * getMeasuredHeight() / 3 * 2.4f;
            } else if (PaperPiecerandRadius.get(i) > 0.8f && PaperPiecerandRadius.get(i) <= 0.9f) {
                centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                        + mAnimatedValue * getMeasuredHeight() / 3 * 2.6f;
            } else if (PaperPiecerandRadius.get(i) > 0.9f) {
                centerY = getMeasuredHeight() / 3 * PaperPiecerandY.get(i) + getMeasuredHeight() / 3

                        + mAnimatedValue * getMeasuredHeight() / 3 * 2.8f;
            }


            float x = (float) ((radius) * Math.cos(angle * Math.PI / 180f));
            float y = (float) ((radius) * Math.sin(angle * Math.PI / 180f));

            paperPiece.reset();
            paperPiece.moveTo(centerX - x,
                    centerY - y

            );


            int angleBig = 0;
            if (PaperPiecerandX.get(i) > 0.7f) {
                angleBig = (int) (180 * 0.7f);
            } else if (PaperPiecerandX.get(i) < 0.3f) {
                angleBig = (int) (180 * 0.3f);
            } else {
                angleBig = (int) (180 * PaperPiecerandX.get(i));
            }
            int angleSmall = 180 - angleBig;

            angle = angle + angleBig;
            x = (float) ((radius) * Math.cos(angle * Math.PI / 180f));
            y = (float) ((radius) * Math.sin(angle * Math.PI / 180f));
            paperPiece.lineTo(centerX - x,
                    centerY - y

            );


            angle = angle + angleSmall;
            x = (float) ((radius) * Math.cos(angle * Math.PI / 180f));
            y = (float) ((radius) * Math.sin(angle * Math.PI / 180f));
            paperPiece.lineTo(centerX - x,
                    centerY - y

            );

            angle = angle + angleBig;
            x = (float) ((radius) * Math.cos(angle * Math.PI / 180f));
            y = (float) ((radius) * Math.sin(angle * Math.PI / 180f));
            paperPiece.lineTo(centerX - x,
                    centerY - y

            );
            angle = angle + angleSmall;
            x = (float) ((radius) * Math.cos(angle * Math.PI / 180f));
            y = (float) ((radius) * Math.sin(angle * Math.PI / 180f));
            paperPiece.lineTo(centerX - x,
                    centerY - y

            );


            paperPiece.close();

            int color = (Integer) evaluator.evaluate(PaperPiecerandRadius.get(i),
                    Color.rgb(180, 180, 180), paperColor
            );

            mPaint.setColor(color);
            canvas.drawPath(paperPiece, mPaint);


        }
        mPaint.setColor(paperColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mPaddingLR = dip2px(getMeasuredWidth() / 30);
        mPaint.setColor(Color.WHITE);
        drawPaper(canvas);
        if (mShrededType == SHREDEDTYPE.Slip)
            drawPaperSlip(canvas);
        else if (mShrededType == SHREDEDTYPE.Piece)
            drawPaperPiece(canvas);
        drawHost(canvas);
        canvas.restore();

    }

    public enum SHREDEDTYPE {
        Slip, Piece
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST
                && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(dip2px(150), dip2px(150));
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(dip2px(150), heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, dip2px(150));
        }
    }

    private float getFontlength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    private float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();

    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void startAnim(int duration) {
        stopAnim();
        startViewAnim(0f, 1f, duration);
    }

    private ValueAnimator valueAnimator;
    private float mAnimatedValue = 0f;

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
            mAnimatedValue = 0f;
            postInvalidate();
        }
    }


    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setRandom();

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);


                setRandom();

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                setRandom();
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }

        return valueAnimator;
    }

    private void setRandom() {
        randomHighs.clear();
        randomQuadWidths.clear();
        randomQuadHighs.clear();
        randomQuadDirection.clear();
        for (int i = 0; i < paperSlipCount; i++) {
            randomHighs.add((float) Math.random());
            randomQuadWidths.add((float) Math.random());
            randomQuadHighs.add((float) Math.random());
            randomQuadDirection.add((float) Math.random());

        }
        PaperPiecerandX.clear();
        PaperPiecerandY.clear();
        PaperPiecerandAngle.clear();
        PaperPiecerandRadius.clear();
        for (int i = 0; i < paperPieceCount; i++) {
            PaperPiecerandX.add((float) Math.random());
            PaperPiecerandY.add((float) Math.random());
            PaperPiecerandAngle.add((float) Math.random());
            PaperPiecerandRadius.add((float) Math.random());
        }


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }
}
