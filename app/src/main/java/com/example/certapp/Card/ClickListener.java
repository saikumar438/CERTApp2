package com.example.certapp.Card;

import android.view.View;

public interface ClickListener<T> {
    void onClick(View view, T data, int position);
}
