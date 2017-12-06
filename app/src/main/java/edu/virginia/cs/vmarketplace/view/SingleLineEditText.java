package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by cutehuazai on 11/24/17.
 */

class SingleLineEditText extends AppCompatEditText
{

    public SingleLineEditText(Context context) {
        super(context);
    }

    public SingleLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER)
        {
            // Just ignore the [Enter] key
            return true;
        }
        // Handle all other keys in the default way
        return super.onKeyDown(keyCode, event);
    }
}