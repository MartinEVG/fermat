package com.bitdubai.reference_niche_wallet.bitcoin_wallet.preference_settings;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.PreferenceWalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettings;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.24..
 */
public class ReferenceWalletPreferenceSettings extends PreferenceWalletSettings{

    private int transactionToShow;

    BalanceType balanceType;

    public ReferenceWalletPreferenceSettings(){
        transactionToShow=6;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public int getTransactionsToShow() {
        return transactionToShow;
    }
}