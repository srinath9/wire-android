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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.util.AttributeSet;
import android.view.View;
import com.waz.api.Self;
import com.waz.api.User;
import com.waz.zclient.controllers.accentcolor.AccentColorObserver;
import com.waz.zclient.controllers.confirmation.TwoButtonConfirmationCallback;
import com.waz.zclient.controllers.sharing.SharedContentType;
import com.waz.zclient.core.stores.api.ZMessagingApiStoreObserver;
import com.waz.zclient.pages.main.sharing.SharingConversationListManagerFragment;
import com.waz.zclient.utils.ViewUtils;
import com.waz.zclient.views.menus.ConfirmationMenu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShareActivity extends BaseActivity implements SharingConversationListManagerFragment.Container,
                                                           AccentColorObserver,
                                                           ZMessagingApiStoreObserver {

    private static final String TAG = ShareActivity.class.getName();

    private ConfirmationMenu confirmationMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_share);

        confirmationMenu = ViewUtils.getView(this, R.id.cm__conversation_list__login_prompt);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.fl_main_content,
                                            SharingConversationListManagerFragment.newInstance(),
                                            SharingConversationListManagerFragment.TAG)
                                       .commit();
        }

        handleIncomingIntent();
    }

    @Override
    protected int getBaseTheme() {
        return R.style.Theme_Dark;
    }

    @Override
    public View onCreateView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onStart() {
        getStoreFactory().getZMessagingApiStore().addApiObserver(this);
        getControllerFactory().getAccentColorController().addAccentColorObserver(this);
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //do nothing
    }

    @Override
    public void onStop() {
        getStoreFactory().getZMessagingApiStore().removeApiObserver(this);
        getControllerFactory().getAccentColorController().removeAccentColorObserver(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        confirmationMenu = null;
        super.onDestroy();
    }

    private void setUserImage(User callingUser) {
        getControllerFactory().getBackgroundController().setImageAsset(callingUser.getPicture());
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //
    //  AccentColorObserver
    //
    //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onAccentColorHasChanged(Object sender, int color) {
        confirmationMenu.setButtonColor(color);
    }


    private void enterApplication(Self self) {
        if (!self.isLoggedIn()) {
            // User not logged in
            showUserNotLoggedInDialog();
        } else if (self.isEmailVerified()) {
            // User logged in
            User callingUser = getStoreFactory().getZMessagingApiStore().getApi().getSelf().getUser();
            setUserImage(callingUser);
        }
    }

    public void showUserNotLoggedInDialog() {
        confirmationMenu.setHeader(getString(R.string.sharing__user_not_logged_in__dialog__title));
        confirmationMenu.setText(getString(R.string.sharing__user_not_logged_in__dialog__message));
        confirmationMenu.setPositiveButton(getString(R.string.sharing__user_not_logged_in__dialog__confirm));
        confirmationMenu.setNegativeButton("");
        confirmationMenu.setCallback(new TwoButtonConfirmationCallback() {
            @Override
            public void positiveButtonClicked(boolean checkboxIsSelected) {
                finish();
            }

            @Override
            public void negativeButtonClicked() {

            }

            @Override
            public void onHideAnimationEnd(boolean confirmed, boolean canceled, boolean checkboxIsSelected) {

            }
        });

        confirmationMenu.animateToShow(true);
    }

    private void handleIncomingIntent() {
        ShareCompat.IntentReader intentReader = ShareCompat.IntentReader.from(this);
        if (!intentReader.isShareIntent()) {
            finish();
            return;
        }
        if (intentReader.getType().equals("text/plain")) {
            getControllerFactory().getSharingController().setSharedContentType(SharedContentType.TEXT);
            getControllerFactory().getSharingController().setSharedText(String.valueOf(intentReader.getText()));
        } else {
            final Set<Uri> sharedFileUris = new HashSet<>();
            Uri stream = intentReader.getStream();
            if (stream != null) {
                sharedFileUris.add(stream);
            }
            for (int i = 0; i < intentReader.getStreamCount(); i++) {
                stream = intentReader.getStream(i);
                if (stream != null) {
                    sharedFileUris.add(stream);
                }
            }
            if (sharedFileUris.size() == 0) {
                finish();
                return;
            }
            boolean sharingImages = intentReader.getType().startsWith("image/");
            final SharedContentType contentType;
            if (sharingImages && sharedFileUris.size() == 1) {
                contentType = SharedContentType.IMAGE;
            } else {
                contentType = SharedContentType.FILE;
            }
            getControllerFactory().getSharingController().setSharedContentType(contentType);
            getControllerFactory().getSharingController().setSharedUris(new ArrayList<>(sharedFileUris));
        }
    }

    @Override
    public void onInitialized(Self self) {
        enterApplication(self);
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onForceClientUpdate() {

    }
}
