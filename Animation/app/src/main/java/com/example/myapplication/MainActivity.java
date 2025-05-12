package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button buttonSlide, buttonBulge, buttonShrink, buttonFade, buttonRotate, buttonBounce, buttonAlpha, buttonScale, buttonTranslate, buttonSlideUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);  // Add a TextView in your layout for "Nidharsan"
        buttonSlide = findViewById(R.id.button_slide);
        buttonBulge = findViewById(R.id.button_bulge);
        buttonShrink = findViewById(R.id.button_shrink);
        buttonFade = findViewById(R.id.button_fade);
        buttonRotate = findViewById(R.id.button_rotate);
        buttonBounce = findViewById(R.id.button_bounce);
        buttonAlpha = findViewById(R.id.button_alpha);
        buttonScale = findViewById(R.id.button_scale);
        buttonTranslate = findViewById(R.id.button_translate);
        buttonSlideUp = findViewById(R.id.button_slide_up);

        buttonSlide.setOnClickListener(v -> applySlideAnimation());
        buttonBulge.setOnClickListener(v -> applyBulgeAnimation());
        buttonShrink.setOnClickListener(v -> applyShrinkAnimation());
        buttonFade.setOnClickListener(v -> applyFadeAnimation());
        buttonRotate.setOnClickListener(v -> applyRotateAnimation());
        buttonBounce.setOnClickListener(v -> applyBounceAnimation());
        buttonAlpha.setOnClickListener(v -> applyAlphaAnimation());
        buttonScale.setOnClickListener(v -> applyScaleAnimation());
        buttonTranslate.setOnClickListener(v -> applyTranslateAnimation());
        buttonSlideUp.setOnClickListener(v -> applySlideUpAnimation());
    }

    private void applySlideAnimation() {
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        textView.startAnimation(slide);
    }

    private void applyBulgeAnimation() {
        Animation bulge = AnimationUtils.loadAnimation(this, R.anim.bulge);
        textView.startAnimation(bulge);
    }

    private void applyShrinkAnimation() {
        Animation shrink = AnimationUtils.loadAnimation(this, R.anim.shrink);
        textView.startAnimation(shrink);
    }

    private void applyFadeAnimation() {
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        textView.startAnimation(fade);
    }

    private void applyRotateAnimation() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        textView.startAnimation(rotate);
    }

    private void applyBounceAnimation() {
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        textView.startAnimation(bounce);
    }

    private void applyAlphaAnimation() {
        Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        textView.startAnimation(alpha);
    }

    private void applyScaleAnimation() {
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        textView.startAnimation(scale);
    }

    private void applyTranslateAnimation() {
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);
        textView.startAnimation(translate);
    }

    private void applySlideUpAnimation() {
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        textView.startAnimation(slideUp);
    }
}
