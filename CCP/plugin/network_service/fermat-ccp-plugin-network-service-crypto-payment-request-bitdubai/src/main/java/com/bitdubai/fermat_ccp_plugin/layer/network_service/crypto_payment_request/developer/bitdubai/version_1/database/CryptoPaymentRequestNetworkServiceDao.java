package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.CryptoPaymentRequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.CryptoPaymentRequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestProtocolStateException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestNetworkServiceRecord;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDao</code>
 * haves all the methods with access to database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestNetworkServiceDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public CryptoPaymentRequestNetworkServiceDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                 final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initialize() throws CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    this.pluginId.toString()
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CryptoPaymentRequestNetworkServiceDatabaseFactory databaseFactory = new CryptoPaymentRequestNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        pluginId.toString()
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(z, "", "Unhandled Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException(e, "", "Unhandled Exception.");
        }
    }

    public void changeProtocolState(UUID                 requestId    ,
                                    RequestProtocolState protocolState) throws CantChangeCryptoPaymentRequestProtocolStateException,
                                                                       RequestNotFoundException                            {

        if (requestId == null)
            throw new CantChangeCryptoPaymentRequestProtocolStateException("requestId null "   , "The requestId is required, can not be null"    );

        if (protocolState == null)
            throw new CantChangeCryptoPaymentRequestProtocolStateException("protocolState null", "The protocolState is required, can not be null");

        try {

            DatabaseTable cryptoPaymentRequestTable = database.getTable(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            cryptoPaymentRequestTable.setUUIDFilter(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME, requestId, DatabaseFilterType.EQUAL);

            cryptoPaymentRequestTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoPaymentRequestTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME, protocolState.getCode());

                cryptoPaymentRequestTable.updateRecord(record);
            } else {
                throw new RequestNotFoundException("RequestId: "+requestId, "Cannot find a CryptoPaymentRequest with the given id.");
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantChangeCryptoPaymentRequestProtocolStateException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantUpdateRecordException exception) {

            throw new CantChangeCryptoPaymentRequestProtocolStateException(exception, "", "Cant update record exception.");
        }
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                      record                    ,
                                                    CryptoPaymentRequestNetworkServiceRecord cryptoPaymentRequestRecord) {

        record.setUUIDValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME         , cryptoPaymentRequestRecord.getRequestId()                                      );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, cryptoPaymentRequestRecord.getIdentityPublicKey()                              );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_TYPE_COLUMN_NAME      , cryptoPaymentRequestRecord.getIdentityType()     .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   , cryptoPaymentRequestRecord.getActorPublicKey()                                 );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_TYPE_COLUMN_NAME         , cryptoPaymentRequestRecord.getActorType()        .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME        , cryptoPaymentRequestRecord.getDescription()                                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     , cryptoPaymentRequestRecord.getCryptoAddress()    .getAddress()                 );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    , cryptoPaymentRequestRecord.getCryptoAddress()    .getCryptoCurrency().getCode());
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TYPE_COLUMN_NAME               , cryptoPaymentRequestRecord.getType()             .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME     , cryptoPaymentRequestRecord.getProtocolState()    .getCode()                    );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME             , cryptoPaymentRequestRecord.getAction()           .getCode()                    );
        record.setLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME             , cryptoPaymentRequestRecord.getAmount()                                         );
        record.setLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME   , cryptoPaymentRequestRecord.getStartTimeStamp()                                 );
        record.setStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_NETWORK_TYPE_COLUMN_NAME       , cryptoPaymentRequestRecord.getNetworkType()      .getCode()                    );

        return record;
    }

    private CryptoPaymentRequest buildAddressExchangeRequestRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId            = record.getUUIDValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME         );
        String identityPublicKey    = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        String identityTypeString   = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_TYPE_COLUMN_NAME      );
        String actorPublicKey       = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   );
        String actorTypeString      = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_TYPE_COLUMN_NAME         );
        String description          = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME        );
        String cryptoAddressString  = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     );
        String cryptoCurrencyString = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    );
        String typeString           = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TYPE_COLUMN_NAME               );
        String actionString         = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME             );
        String protocolStateString  = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME     );
        String networkTypeString    = record.getStringValue(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_NETWORK_TYPE_COLUMN_NAME       );

        long   amount               = record.getLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME             );
        long   startTimeStamp       = record.getLongValue  (CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME   );

        CryptoAddress        cryptoAddress = new CryptoAddress(cryptoAddressString, CryptoCurrency.getByCode(cryptoCurrencyString));

        CryptoPaymentRequestType     type          = CryptoPaymentRequestType  .getByCode(typeString)         ;
        CryptoPaymentRequestAction   action        = CryptoPaymentRequestAction.getByCode(actionString)       ;
        RequestProtocolState         protocolState = RequestProtocolState      .getByCode(protocolStateString);
        BlockchainNetworkType        networkType   = BlockchainNetworkType     .getByCode(networkTypeString)  ;

        Actors identityType = Actors.getByCode(identityTypeString);
        Actors actorType    = Actors.getByCode(actorTypeString   );

        return new CryptoPaymentRequestNetworkServiceRecord(
                requestId        ,
                identityPublicKey,
                identityType     ,
                actorPublicKey   ,
                actorType        ,
                description      ,
                cryptoAddress    ,
                amount           ,
                startTimeStamp   ,
                type             ,
                action           ,
                protocolState    ,
                networkType
        );
    }

}