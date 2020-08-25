package ghelani.kshamina.sssc_android_app.ui.utils.list.model;

public class TextItem  implements DiffItem {

    private String text;
    private boolean marginTop;

    public TextItem(String text,boolean marginTop) {
        this.text = text;
        this.marginTop = marginTop;
    }

    public TextItem(String text) {
        this.text = text;
        marginTop = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMarginTop() {
        return marginTop;
    }

    public void setMarginTop(boolean marginTop) {
        this.marginTop = marginTop;
    }
}
