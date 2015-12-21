package com.parttime.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.parttime.R;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Jumy on 15/12/21 下午3:32.
 * deadline is the first productivity
 */
@EViewGroup(R.layout.ui_new_job_item)
public class NewJobItem extends LinearLayout{
    private static final String TAG = NewJobItem.class.getSimpleName();

    @ViewById(R.id.item_new_job_name)
    TextView mName;
    @ViewById(R.id.item_new_job_edit)
    EditText mEdit;

    public void setItmeName(String name){
        mName.setText(name);
    }

    public String getEditString(){
        return mEdit.getText().toString();
    }

    public NewJobItem(Context context) {
        super(context);
    }

    public NewJobItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewJobItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
