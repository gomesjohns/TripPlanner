package tripPlanner.helper;

import android.support.design.widget.TextInputEditText;
import android.widget.AutoCompleteTextView;

public class InputValidation {

    private int errorCount;
    public InputValidation()
    { }

    public void validateTextViewInput(TextInputEditText textInputEditText, String string)
    {
        if(string.isEmpty()) {
            textInputEditText.setError("This field cannot be empty");
            errorCount++;
        }
        else {
            textInputEditText.setError(null);
        }
    }

    public void validateInputAutoComplete(AutoCompleteTextView autoCompleteTextView, String string)
    {
        if(string.isEmpty()) {
            autoCompleteTextView.setError("This field cannot be empty");
            errorCount++;
        }
        else {
            autoCompleteTextView.setError(null);
        }
    }

    public int getErrorCount()
    {
        return errorCount;
    }
    public void setErrorCount() {
        this.errorCount = 0;
    }

}
