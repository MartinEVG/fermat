/*
 * @#CompleteComponentConnectionRequestPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.CompleteComponentConnectionRequestPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST</code> is receive by the server.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 14/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentConnectionRequestPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        /*
         * Get the filters from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getServerIdentity());

        System.out.println("CompleteComponentConnectionRequestPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject respond = parser.parse(messageContentJsonStringRepresentation).getAsJsonObject();
        NetworkServiceType networkServiceType = gson.fromJson(respond.get(AttNamesConstants.JSON_ATT_NAME_NETWORK_SERVICE_TYPE), NetworkServiceType.class);
        String remoteIdentity                 = respond.getAsString();

        System.out.println("CompleteComponentConnectionRequestPacketProcessor - networkServiceType = "+networkServiceType);
        System.out.println("CompleteComponentConnectionRequestPacketProcessor - remoteIdentity = " + remoteIdentity);

        /*
         * Create a new event whit the networkServiceType and remoteIdentity
         */
        FermatEvent event = EventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * Configure the values
         */
        ((CompleteComponentConnectionRequestNotificationEvent)event).setNetworkServiceType(networkServiceType);
        ((CompleteComponentConnectionRequestNotificationEvent)event).setRemoteIdentity(remoteIdentity);

        /*
         * Raise the event
         */
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

        System.out.println("CompleteComponentConnectionRequestPacketProcessor - Fire a event = EventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION");

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST;
    }
}