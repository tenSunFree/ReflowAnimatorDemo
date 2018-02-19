package com.example.administrator.reflowanimatordemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.shazam.android.widget.text.reflow.ReflowTextAnimatorHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView fromView = findViewById(R.id.fromText);
        final TextView toView = findViewById(R.id.toText);

        // Both views need to have the same text for the transformation to make sense.
        String text = "❄ Welcome ❄";
        fromView.setText(text);
        toView.setText(text);

        // Views have to be laid out so the helper can calculate the transformation needed.
        fromView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fromView.getViewTreeObserver().removeOnPreDrawListener(this);

                Animator animator = new ReflowTextAnimatorHelper.Builder(fromView, toView)
                        .withDuration(2000, 3000)
                        .buildAnimator();

                animator.setStartDelay(500);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Swap out the original view now that it looks the same as the target
                        fromView.setVisibility(View.GONE);
                        toView.setVisibility(View.VISIBLE);
                    }
                });
                animator.start();

                return true;
            }
        });
    }
}
