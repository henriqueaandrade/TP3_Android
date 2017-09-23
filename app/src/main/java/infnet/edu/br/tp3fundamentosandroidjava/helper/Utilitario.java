package infnet.edu.br.tp3fundamentosandroidjava.helper;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by joaoluisdomingosxavier on 20/09/17.
 */

public class Utilitario {

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
