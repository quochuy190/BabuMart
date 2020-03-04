package com.vn.babumart.callback;

public interface ItemClickListener<T> {
    void onClickItem(int position, T item);
}
