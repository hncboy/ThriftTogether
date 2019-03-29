package com.pro516.thrifttogether.entity.home;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * Created by hncboy on 2019-03-28.
 */
public class BannerInfo extends SimpleBannerInfo {

    private String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public Object getXBannerUrl() {
        return imageUrl;
    }
}
