package cn.kanejin.textslider.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.kanejin.textslider.TextItem;
import cn.kanejin.textslider.TextSlider;
import cn.kanejin.textslider.TextSliderListener;

public class MainActivity extends AppCompatActivity {

    private TextSlider mTextSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTextSlider();
    }

    private void initTextSlider() {
        mTextSlider = (TextSlider) findViewById(R.id.text_slider);

        DemoTextSliderAdpter adapter = new DemoTextSliderAdpter(this);

        mTextSlider.setAdapter(adapter);

        mTextSlider.setListener(new TextSliderListener() {
            @Override
            public void onTextClick(TextSlider textSlider, int position, TextItem text) {
                Toast.makeText(MainActivity.this, text.getUrl(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextSlider.play();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mTextSlider.pause();
    }
}