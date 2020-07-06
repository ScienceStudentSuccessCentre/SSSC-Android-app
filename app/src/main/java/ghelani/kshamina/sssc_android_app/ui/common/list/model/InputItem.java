package ghelani.kshamina.sssc_android_app.ui.common.list.model;

import ghelani.kshamina.sssc_android_app.ui.common.events.InputItemListener;

public class InputItem implements DiffItem{

    public enum InputType {
        BUTTON,
        TEXT,
        SWITCH,

    }

    private String value;
    private String hint;
    private String name;
    private InputItemListener listener;
    private boolean switchInput;
    private InputType type;

    public InputItem(String value, String hint, String name, InputItemListener listener, InputType type) {
        this.value = value;
        this.hint = hint;
        this.name = name;
        this.listener = listener;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputItemListener getListener() {
        return listener;
    }

    public void setListener(InputItemListener listener) {
        this.listener = listener;
    }

    public boolean isSwitchInput() {
        return switchInput;
    }

    public void setSwitchInput(boolean switchInput) {
        this.switchInput = switchInput;
    }

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }
}
