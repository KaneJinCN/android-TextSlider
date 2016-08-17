# Android文本滑动器

## Preview 效果预览

![TextSlider Preview 1 Gif](https://cloud.githubusercontent.com/assets/7828293/17703617/f217290e-6404-11e6-8be1-860d0b70ee16.gif)
![TextSlider Preview 2 Gif](https://cloud.githubusercontent.com/assets/7828293/17703563/bab9f95a-6404-11e6-8520-00bbea5f272b.gif)

## Usage 使用方法
1. 引用TextSlider Library

    在build.gradle中添加依赖
    ```gradle
    compile 'cn.kanejin.textslider:library:1.0.1@aar'
    ```

2. 在layout里定义AdBox
    ```xml
    <cn.kanejin.textslider.TextSlider
        android:id="@+id/text_slider"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:paddingLeft="8dp"
        android:background="#ffe822"
        custom:textslider_textColor="@android:color/black"
        custom:textslider_textSize="16sp"
        custom:textslider_duration="500"
        custom:textslider_delay="5000"
        custom:textslider_loop="true"
        custom:textslider_autoPlay="true"
        custom:textslider_rows="3"
        custom:textslider_step="2"
        custom:textslider_rowHeight="36dp" />

    ```
    [查看完整的示例代码](https://github.com/KaneJinCN/android-TextSlider/blob/master/demo/src/main/res/layout/activity_main.xml)

3. 在Activity里设置Adapter和Listener
    ```java
    mTextSlider = (TextSlider) findViewById(R.id.text_slider);

    DemoTextSliderAdpter adapter = new DemoTextSliderAdpter(this);

    mTextSlider.setAdapter(adapter);

    mTextSlider.setListener(new TextSliderListener() {
        @Override
        public void onTextClick(TextSlider textSlider, int position, TextItem text) {
            Toast.makeText(MainActivity.this, text.getUrl(), Toast.LENGTH_SHORT).show();
        }
    });
    ```

    [查看完整的示例代码](https://github.com/KaneJinCN/android-TextSlider/blob/master/demo/src/main/java/cn/kanejin/textslider/demo/MainActivity.java)

##  Attributes 参数说明


<table style="width:100%;">
<tr>
<th>参数名</th><th>类型</th><th>默认值</th><th>说明</th>
</tr>

<tr>
<td>textslider_textColor</td>
<td>color</td>
<td>&nbsp;</td>
<td>文本颜色</td>
</tr>

<tr>
<td>textslider_textSize</td>
<td>dimension</td>
<td>16sp</td>
<td>文本字体大小</td>
</tr>

<tr>
<td>textslider_duration</td>
<td>integer</td>
<td>1000</td>
<td>广告切换动画时间, 单位ms</td>
</tr>

<tr>
<td>textslider_delay</td>
<td>integer</td>
<td>5000</td>
<td>广告停留时间, 单位ms</td>
</tr>

<tr>
<td>textslider_loop</td>
<td>boolean</td>
<td>true</td>
<td>是否循环播放</td>
</tr>

<tr>
<td>textslider_autoPlay</td>
<td>boolean</td>
<td>true</td>
<td>是否自动播放</td>
</tr>

<tr>
<td>textslider_rows</td>
<td>integer</td>
<td>1</td>
<td>显示行数</td>
</tr>

<tr>
<td>textslider_rowHeight</td>
<td>dimension</td>
<td>24dp</td>
<td>每行高度</td>
</tr>

<tr>
<td>textslider_step</td>
<td>integer</td>
<td>1</td>
<td>每次滑动行数</td>
</tr>

</table>

## License 许可
[MIT](https://github.com/KaneJinCN/android-TextSlider/blob/master/LICENSE)

## See Also 参阅
[AdBox](https://github.com/KaneJinCN/android-AdBox)