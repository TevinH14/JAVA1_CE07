package com.example.hamiltontevin_ce07;

class Books {
    private final String mTitle;
    private final String mImage;

    public Books(String mTitle, String mImage) {
        this.mTitle = mTitle;
        this.mImage = mImage;
    }

    public String getTitle(){return mTitle;}
    public String getImage(){return mImage;}
}
