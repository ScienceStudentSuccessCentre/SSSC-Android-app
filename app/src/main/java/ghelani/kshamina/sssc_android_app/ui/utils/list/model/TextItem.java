package ghelani.kshamina.sssc_android_app.ui.utils.list.model;

public class TextItem  implements DiffItem {

    private String text;

    public TextItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
