package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.DeveloperLoginFailedException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentityManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperIdentityPluginRoot implements DeveloperIdentityManager, DealsWithErrors, Service, Plugin {


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DeviceUser Interface member variables
     */
    private UUID pluginId;

    /**
     * ServiceStatus Interface member variables
     */
    ServiceStatus serviceStatus;

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        if (errorManager == null)
            throw new IllegalArgumentException();
        this.errorManager = errorManager;
    }

    @Override
    public List<DeveloperIdentity> getDevelopersFromActualUser() throws CantGetUserDeveloperIdentitiesException {
        return null;
    }

    @Override
    public void createNewDeveloper(String alias) throws CantCreateNewDeveloperException {

    }

    @Override
    public void login(String alias) throws DeveloperLoginFailedException {

    }

    @Override
    public DeveloperIdentity getActualDeveloper() throws CantGetDeveloperException {
        return null;
    }
}