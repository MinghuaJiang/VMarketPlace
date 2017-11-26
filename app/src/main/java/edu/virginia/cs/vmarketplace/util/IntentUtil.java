package edu.virginia.cs.vmarketplace.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.view.MainActivity;

/**
 * Created by cutehuazai on 11/25/17.
 */

public class IntentUtil {

    public static Intent jumpWithTabRecorded(int tab, Context context, Class clazz){
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(context, clazz);

        bundle.putInt(AppConstant.SWITCH_TAB, tab); // Both constants are defined in your code
        intent.putExtras(bundle);

        return intent;
    }
}
