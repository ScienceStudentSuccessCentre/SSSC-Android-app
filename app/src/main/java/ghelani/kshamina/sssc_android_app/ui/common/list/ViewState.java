package ghelani.kshamina.sssc_android_app.ui.common.list;

import java.util.List;

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
