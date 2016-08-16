package cn.kanejin.textslider;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kane on 8/12/16.
 */
public class TextSliderAdapter {
    private static final String TAG = TextSliderAdapter.class.getSimpleName();

    private List<TextItem> mAdItems = new ArrayList<>();
    private final Object mLock = new Object();

    private Context mContext;

    public TextSliderAdapter(Context context) {
        this.mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    public int getCount() {
        return mAdItems.size();
    }

    protected void setItems(List<? extends TextItem> items) {
        synchronized (mLock) {
            this.mAdItems.clear();
            this.mAdItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void addItem(TextItem item) {
        synchronized (mLock) {
            mAdItems.add(item);
        }
        notifyDataSetChanged();
    }
    public void insertItem(TextItem item, int position) {
        synchronized (mLock) {
            mAdItems.add(position, item);
        }
        notifyDataSetChanged();
    }

    public TextItem getItem(int position) {
        return mAdItems.get(position);
    }

    public boolean isEmpty() {
        return mAdItems.isEmpty();
    }

    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

}
