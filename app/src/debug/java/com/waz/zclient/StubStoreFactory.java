/**
 * Wire
 * Copyright (C) 2016 Wire Swiss GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.waz.zclient;

import com.waz.zclient.core.stores.IStoreFactory;
import com.waz.zclient.core.stores.api.IZMessagingApiStore;
import com.waz.zclient.core.stores.appentry.IAppEntryStore;
import com.waz.zclient.core.stores.connect.IConnectStore;
import com.waz.zclient.core.stores.conversation.IConversationStore;
import com.waz.zclient.core.stores.draft.IDraftStore;
import com.waz.zclient.core.stores.inappnotification.IInAppNotificationStore;
import com.waz.zclient.core.stores.media.IMediaStore;
import com.waz.zclient.core.stores.network.INetworkStore;
import com.waz.zclient.core.stores.participants.IParticipantsStore;
import com.waz.zclient.core.stores.pickuser.IPickUserStore;
import com.waz.zclient.core.stores.profile.IProfileStore;
import com.waz.zclient.core.stores.singleparticipants.ISingleParticipantStore;
import com.waz.zclient.core.stores.stub.StubZMessagingApiStore;

/**
 * These classes are NOT auto generated because of the one or two controllers or stores they need to return.
 */
public class StubStoreFactory implements IStoreFactory {

    @Override
    public void tearDown() {

    }

    @Override
    public boolean isTornDown() {
        return false;
    }

    @Override
    public IAppEntryStore getAppEntryStore() {
        return null;
    }

    @Override
    public IConversationStore getConversationStore() {
        return null;
    }

    @Override
    public IProfileStore getProfileStore() {
        return null;
    }

    @Override
    public IPickUserStore getPickUserStore() {
        return null;
    }

    @Override
    public IConnectStore getConnectStore() {
        return null;
    }

    @Override
    public IParticipantsStore getParticipantsStore() {
        return null;
    }

    @Override
    public ISingleParticipantStore getSingleParticipantStore() {
        return null;
    }

    @Override
    public IInAppNotificationStore getInAppNotificationStore() {
        return null;
    }

    @Override
    public IDraftStore getDraftStore() {
        return null;
    }

    @Override
    public IMediaStore getMediaStore() {
        return null;
    }

    /**
     * We need to provide a non-null ZmessagingApiStore so that the test sub classes of BaseActivity can function without
     * crashing the tests.
     */
    @Override
    public IZMessagingApiStore getZMessagingApiStore() {
        return new StubZMessagingApiStore();
    }

    @Override
    public INetworkStore getNetworkStore() {
        return null;
    }

    @Override
    public void reset() {

    }
}


