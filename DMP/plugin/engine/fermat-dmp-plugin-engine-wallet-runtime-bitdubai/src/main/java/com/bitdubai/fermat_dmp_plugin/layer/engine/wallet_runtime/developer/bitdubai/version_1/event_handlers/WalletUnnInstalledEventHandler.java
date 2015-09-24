package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRemoveWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletInstalledEvent;

/**
 * Created by Matias Furszyfer
 */
public class WalletUnnInstalledEventHandler implements FermatEventHandler {
    WalletRuntimeManager walletRuntimeManager;
    
    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        String publicKey = ((WalletInstalledEvent) fermatEvent).getPublicKey();
        
        
        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {
            
            try {

                this.walletRuntimeManager.removeNavigationStructure(publicKey);

            } catch (CantRemoveWalletNavigationStructureException cantRemoveWalletNavigationStructureException){
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                throw cantRemoveWalletNavigationStructureException;

            }
            
        } 
    }
}
