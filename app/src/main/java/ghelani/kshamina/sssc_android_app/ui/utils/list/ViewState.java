package ghelani.kshamina.sssc_android_app.ui.utils.list;

import java.util.List;

/**
 * The state for ViewModels that are fetching item from the database.
 * This way they can be one of three states:
 * 1) Loading --> display loading icon to the screen
 * 2) Success --> items retrieved so display the list to the screen
 * 3) Error   --> There was some error return error message to the screen
 *
 *
 * @param <T> The type of item being displayed
 */
public class ViewState<T> {
    private boolean isLoading;
    private boolean isError;
    private boolean isSuccess;
    private String error;
    private List<T> items;

    public ViewState(boolean isLoading, boolean isError, boolean isSuccess, String error, List<T> items) {
        this.isLoading = isLoading;
        this.isError = isError;
        this.isSuccess = isSuccess;
        this.error = error;
        this.items = items;
    }

    public boolean isError() {
        return isError;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
