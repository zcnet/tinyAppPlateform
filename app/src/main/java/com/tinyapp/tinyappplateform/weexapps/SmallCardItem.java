package com.tinyapp.tinyappplateform.weexapps;

import com.tinyapp.tinyappplateform.bean.AppBean;
import com.tinyapp.tinyappplateform.bean.Card;

public class SmallCardItem {
    public AppBean app;
    public Card card;
    public SmallCardItem (AppBean app, Card c){
        this.app = app;
        this.card = c;
    }
}
