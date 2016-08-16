package cn.kanejin.textslider.demo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.kanejin.textslider.TextSliderAdapter;

/**
 * Created by Kane on 8/4/16.
 */
public class DemoTextSliderAdpter extends TextSliderAdapter {

    public DemoTextSliderAdpter(Context context) {
        super(context);

        fillAdItems();
    }

    private static final String[] TEXTS = {
            "2016-08-15 0博尔特史诗般奥运夺百米三连冠",
            "2016-08-15 1第15金！孟苏平举重夺冠",
            "2016-08-15 2秦凯现场求婚何姿",
            "2016-08-15 3女排1-3美国小组第4"
    };

    private void fillAdItems() {
        List<DemoTextItem> items = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            DemoTextItem item = new DemoTextItem();

            item.setId((long)i);
            item.setText(TEXTS[i]);
            item.setUrl("http://example.com/text/link/" + i);

            items.add(item);

        }

        setItems(items);
    }
}
