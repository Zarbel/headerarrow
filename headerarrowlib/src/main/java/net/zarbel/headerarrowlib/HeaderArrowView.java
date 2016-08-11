package net.zarbel.headerarrowlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.zarbel.headerarrowlib.listeners.OnPreIconAnimationListener;
import net.zarbel.headerarrowlib.listeners.OnVisibilityChangeListener;

/**
 * Created by zarbel on 1/8/16.
 *
 * HeaderArrowView. View with a header and a content hidden. To open the content,
 * user should click to header.
 *
 * Copyright (C) 2016 Julián Zaragoza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
public class HeaderArrowView extends LinearLayout implements View.OnClickListener, OnPreIconAnimationListener {

    public static final int LEFT = 0;                                           //Constant defining left position to header icon
    public static final int RIGHT = 1;                                          //Constant defining right position to header icon

    public static final int DURATION = 500;                                     //Animation duration

    public static final String SUPER_STATE = "superState";
    public static final String IS_SHOWN = "isShown";
    public static final String HEIGHT = "height";
    public static final String TITLE = "title";
    public static final String IS_ICON_RIGHT = "isIconRight";
    public static final String MIN_HEIGHT = "minHeight";
    public static final String ID_LAYOUT = "idLayout";
    public static final String ID_ICON = "idIcon";
    public static final String HEADER_ICON_COLOR = "headerIconColor";
    public static final String TITLE_COLOR = "titleColor";
    public static final String HEADER_BACKGROUND_COLOR = "headerBackgroundColor";
    public static final String CONTENT_BACKGROUND_COLOR = "contentBackgroundColor";
    public static final String ID_HEADER_BACKGROUND = "idHeaderBackground";
    public static final String ID_CONTENT_BACKGROUND = "idContentBackground";

    private View view;                                                          //Root inflated view
    private LinearLayout llayRoot, llayHeader, llayContent, llayTitleContainer; //Layouts: Root layout, header, content (inflated view) and title container (Only textvie)
    private ImageView imgLeft, imgRight;                                        //Left and right images of header
    private TextView tvTitle;                                                   //Text of title
    private ScrollView scrollView;                                              //ScrollView containing the content layout
    private View contentView;                                                   //Inflated view

    private String title;                                                       //Title of header

    private boolean isIconRight = true;                                         //If icon is on right or on left position
    private boolean isShown = false;                                             //If content is showing or not
    private float minHeight;                                                    //MinHeight of content,

    private int idLayout = -1;                                                  //Layout to inflate, saved on 'contentView'.
    private int height = -1;                                                    //Height of content, got programmatically
    private int idIcon = R.drawable.ic_action_show_hide;                          //Icon of header
    private int headerIconColor;                                                //Color of icon on header
    private int titleColor;                                                     //Text color of title

    private int headerBackgroundColor = -1;                                     //Tint color for header background
    private int contentBackgroundColor = -1;                                    //Tint colro for content background

    @DrawableRes
    private int idHeaderBackground = R.drawable.ha_header_background;           //Drawable for header

    @DrawableRes
    private int idContentBackground = R.drawable.ha_content_background;         //Drawable for content

    //listeners

    private OnPreIconAnimationListener onPreIconAnimationListener = this;       //Animation listener for icon
    private OnVisibilityChangeListener onVisibilityChangeListener = null;       //Visibility change listener


    private boolean isRestoring = false;

    /**
     * Constructor with content view passed by parameter
     * @param context
     * @param contentView
     */
    public HeaderArrowView(Context context, View contentView) {
        super(context);
        this.contentView = contentView;
        setId(R.id.img_left);
        getViews();
        setLogic();
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public HeaderArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        headerIconColor = ContextCompat.getColor(getContext(), R.color.white);
        titleColor = ContextCompat.getColor(getContext(), R.color.white);

        init(attrs);

    }

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public HeaderArrowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Initialize view
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        setAttrs(attrs);
        getViews();
        setListeners();
        setCustomColors();
        setLogic();
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderArrowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    /**
     * Obtaining views
     */
    private void getViews(){

        view = LayoutInflater.from(getContext()).inflate(R.layout.view_header_arrow_layout, this, false);
        addView(view);
        llayRoot = (LinearLayout) view.findViewById(R.id.llay_root);
        llayHeader = (LinearLayout) view.findViewById(R.id.llay_header);
        llayContent = (LinearLayout) view.findViewById(R.id.llay_content);
        llayTitleContainer = (LinearLayout) view.findViewById(R.id.llay_title_container);
        imgLeft = (ImageView) view.findViewById(R.id.img_left);
        imgRight = (ImageView) view.findViewById(R.id.img_right);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        scrollView = (ScrollView) view.findViewById(R.id.scrll_content);
        minHeight = getResources().getDimensionPixelSize(R.dimen.content_min_height);

        if(-1 != idLayout){
            contentView = LayoutInflater.from(getContext()).inflate(idLayout, llayContent, true);
        }
    }

    /**
     * Tinting with custom colors
     */
    private void setCustomColors() {
        Drawable headerBackground = ContextCompat.getDrawable(getContext(), idHeaderBackground);
        Drawable contentBackground = ContextCompat.getDrawable(getContext(), idContentBackground);

        if(headerBackgroundColor != -1){
            headerBackground.setColorFilter(headerBackgroundColor, PorterDuff.Mode.SRC_ATOP);
            headerBackground.setFilterBitmap(true);
        }

        if(contentBackgroundColor != -1){
            contentBackground.setColorFilter(contentBackgroundColor, PorterDuff.Mode.SRC_ATOP);
            contentBackground.setFilterBitmap(true);
        }

        llayHeader.setBackground(headerBackground);
        llayContent.setBackground(contentBackground);
    }

    /**
     * Setting listeners of views
     */
    private void setListeners() {
        tvTitle.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        llayTitleContainer.setOnClickListener(this);
    }

    /**
     * gets attributes of xml
     * @param attrs
     */
    public void setAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HeaderArrowView,
                0, 0);

        try {
            idLayout = a.getResourceId(R.styleable.HeaderArrowView_content_layout, -1);
            isIconRight = RIGHT == a.getInt(R.styleable.HeaderArrowView_header_icon_position, RIGHT);
            title = a.getString(R.styleable.HeaderArrowView_header_title);
            idIcon = a.getResourceId(R.styleable.HeaderArrowView_header_icon, R.drawable.ic_action_show_hide);
            idHeaderBackground = a.getResourceId(R.styleable.HeaderArrowView_header_background_resource, R.drawable.ha_header_background);
            idContentBackground = a.getResourceId(R.styleable.HeaderArrowView_content_background_resource, R.drawable.ha_content_background);
            headerIconColor = a.getColor(R.styleable.HeaderArrowView_header_icon_color, ContextCompat.getColor(getContext(), R.color.white));
            titleColor = a.getColor(R.styleable.HeaderArrowView_title_color, ContextCompat.getColor(getContext(), R.color.white));
            headerBackgroundColor = a.getColor(R.styleable.HeaderArrowView_header_background_tint_color, -1);
            contentBackgroundColor = a.getColor(R.styleable.HeaderArrowView_content_background_tint_color,  -1);

        } catch(Exception e){
            Log.e(getContext().getPackageName(), "Exception", e);
        } finally {
            a.recycle();
        }

    }

    /**
     * Initializes the logic of view
     */
    private void setLogic(){
        preLogic();

        if(!isShown && height == -1) {
            //hides content
            post(new Runnable() {
                @Override
                public void run() {
                    if(!isRestoring) {
                        scrollView.setPivotX(0);
                        scrollView.setPivotY(0);

                        height = scrollView.getHeight();
                        hide(0);
                    }
                }
            });
        }
    }

    private void preLogic() {
        tvTitle.setText(title);

        //Set icoin visibility
        if(isIconRight){
            imgRight.setVisibility(View.VISIBLE);
            imgLeft.setVisibility(View.INVISIBLE);
            imgRight.setImageDrawable(ContextCompat.getDrawable(getContext(), idIcon));
        }else {
            imgRight.setVisibility(View.INVISIBLE);
            imgLeft.setVisibility(View.VISIBLE);
            imgLeft.setImageDrawable(ContextCompat.getDrawable(getContext(), idIcon));
        }

        //tint header
        tint();
    }

    /**
     * Tints header
     */
    private void tint(){
        imgLeft.setColorFilter(headerIconColor);
        imgRight.setColorFilter(headerIconColor);
        tvTitle.setTextColor(titleColor);
    }

    /**
     * Click on header
     * @param view
     */
    @Override
    public void onClick(View view) {

        //Case tvTitle
        if((view.getId() == R.id.tv_title || view.getId() == R.id.img_left || view.getId() == R.id.img_right
                || view.getId() == R.id.llay_title_container) && isShown){

            hide();

        }else if ((view.getId() == R.id.tv_title || view.getId() == R.id.img_left || view.getId() == R.id.img_right
                || view.getId() == R.id.llay_title_container) && !isShown){

            show();

        }

    }


    /**
     * Hide animation, only used on first hide
     * @param duration
     */
    private void hide(final int duration){
        //Listener invocation
        if(isIconRight){
            onPreIconAnimationListener.onPreHide(imgRight);
        }else{
            onPreIconAnimationListener.onPreHide(imgLeft);
        }

        isShown = false;

        ValueAnimator va = ValueAnimator.ofInt(height, (int)minHeight);
        va.setDuration(duration);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                scrollView.getLayoutParams().height = value.intValue();
                scrollView.requestLayout();
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(null != onVisibilityChangeListener && duration > 0){
                    onVisibilityChangeListener.onContentHide();
                }
            }
        });

        va.start();
    }

    /**
     * hide animation with standard duration
     */
    private void hide() {
        hide(DURATION);
    }

    /**
     * Show animation
     */
    private void show(final int duration) {
        if(isIconRight){
            onPreIconAnimationListener.onPreShow(imgRight);
        }else{
            onPreIconAnimationListener.onPreShow(imgLeft);
        }

        isShown = true;

        ValueAnimator va = ValueAnimator.ofInt((int)minHeight, height);
        va.setDuration(duration);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                scrollView.getLayoutParams().height = value.intValue();
                scrollView.requestLayout();
            }
        });

        //Content show listener
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(null != onVisibilityChangeListener && duration > 0){
                    onVisibilityChangeListener.onContentShow();
                }
            }
        });

        va.start();
    }

    private void show(){
        show(DURATION);
    }

    @Override
    public void onPreShow(ImageView headerImageView) {

        imgLeft.animate().rotation(180);
        imgRight.animate().rotation(180);

    }

    @Override
    public void onPreHide(ImageView headerImageView) {

        imgLeft.animate().rotation(0);
        imgRight.animate().rotation(0);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE, superState);

        bundle.putBoolean(IS_SHOWN , isShown);
        bundle.putInt(HEIGHT , height);
        bundle.putString(TITLE , title);
        bundle.putBoolean(IS_ICON_RIGHT , isIconRight);
        bundle.putFloat(MIN_HEIGHT , minHeight);
        bundle.putInt(ID_LAYOUT , idLayout);
        bundle.putInt(ID_ICON , idIcon);
        bundle.putInt(HEADER_ICON_COLOR , headerIconColor);
        bundle.putInt(TITLE_COLOR , titleColor);
        bundle.putInt(HEADER_BACKGROUND_COLOR , headerBackgroundColor);
        bundle.putInt(CONTENT_BACKGROUND_COLOR , contentBackgroundColor);
        bundle.putInt(ID_HEADER_BACKGROUND , idHeaderBackground);
        bundle.putInt(ID_CONTENT_BACKGROUND , idContentBackground);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            isRestoring = true;
            Bundle bundle = (Bundle) state;
            isShown = bundle.getBoolean(IS_SHOWN);
            height = bundle.getInt(HEIGHT);
            title = bundle.getString(TITLE);
            isIconRight = bundle.getBoolean(IS_ICON_RIGHT);
            minHeight = bundle.getFloat(MIN_HEIGHT);
            idLayout = bundle.getInt(ID_LAYOUT);
            idIcon = bundle.getInt(ID_ICON);
            headerIconColor = bundle.getInt(HEADER_ICON_COLOR);
            titleColor = bundle.getInt(TITLE_COLOR);
            headerBackgroundColor = bundle.getInt(HEADER_BACKGROUND_COLOR);
            contentBackgroundColor = bundle.getInt(CONTENT_BACKGROUND_COLOR);
            idHeaderBackground = bundle.getInt(ID_HEADER_BACKGROUND);
            idContentBackground =bundle.getInt(ID_CONTENT_BACKGROUND);

            post(new Runnable() {
                @Override
                public void run() {

                    if(isShown){
                        show(0);
                    }else{
                        hide(0);
                    }
                    isRestoring = false;
                }
            });

            super.onRestoreInstanceState(bundle.getParcelable(SUPER_STATE));

        }else{

            super.onRestoreInstanceState(state);

        }

    }

    /**
     * Sets the listener to allow custom animations of icon
     * @param onPreIconAnimationListener
     */
    public void setOnPreIconAnimationListener(OnPreIconAnimationListener onPreIconAnimationListener) {
        this.onPreIconAnimationListener = onPreIconAnimationListener;
    }

    /**
     * Sets the visibility listener
     * @param onVisibilityChangeListener
     */
    public void addOnVisibilityChangeListener(OnVisibilityChangeListener onVisibilityChangeListener) {
        this.onVisibilityChangeListener = onVisibilityChangeListener;
    }
}
