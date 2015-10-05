package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletBalance implements AssetUserWalletList {
    String assetPublicKey;
    String name;
    String description;
    long bookBalance;
    long availableBalance;

    public AssetUserWalletBalance(String assetPublicKey, String name, String description, long bookBalance, long availableBalance){
        this.assetPublicKey = assetPublicKey;
        this.name = name;
        this.description = description;
        this.bookBalance = bookBalance;
        this.availableBalance = availableBalance;
    };

    @Override
    public String getAssetPublicKey() {
        return assetPublicKey;
    }

    @Override
    public void setAssetPublicKey(String assetPublicKey) {
        this.assetPublicKey = assetPublicKey;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getBookBalance() {
        return bookBalance;
    }

    @Override
    public void setBookBalance(long bookBalance) {
        this.bookBalance = bookBalance;
    }

    @Override
    public long getAvailableBalance() {
        return availableBalance;
    }

    @Override
    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }
}
