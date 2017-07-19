package org.standardnotes.notes.comms.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExportItems {

    @SerializedName("items")
    @Expose
    private List<EncryptableItem> items = new ArrayList<EncryptableItem>();

    public List<EncryptableItem> getItems() {
        return items;
    }

    public void setItems(List<EncryptableItem> items) {
        this.items = items;
    }

}
