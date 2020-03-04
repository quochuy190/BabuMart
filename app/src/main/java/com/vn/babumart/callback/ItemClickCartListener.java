package com.vn.babumart.callback;

public interface ItemClickCartListener<T> {
    void onClickItem(int position, T item);
    void onClickItem_Add(int position, T item);
    void onClickItem_Minus(int position, T item);
}
