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
package com.waz.zclient.pages.main.profile.preferences;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.widget.Toast;
import com.waz.api.ZmsVersion;
import com.waz.zclient.R;
import com.waz.zclient.pages.BasePreferenceFragment;
import timber.log.Timber;

public class AboutPreferences extends BasePreferenceFragment<AboutPreferences.Container> {

    private static final int A_BUNCH_OF_CLICKS_TO_PREVENT_ACCIDENTAL_TRIGGERING = 10;
    private int clickCounter;

    public static AboutPreferences newInstance(String rootKey, Bundle extras) {
        AboutPreferences f = new AboutPreferences();
        Bundle args = extras == null ? new Bundle() : new Bundle(extras);
        args.putString(ARG_PREFERENCE_ROOT, rootKey);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreatePreferences2(Bundle savedInstanceState, String rootKey) {
        super.onCreatePreferences2(savedInstanceState, rootKey);
        addPreferencesFromResource(R.xml.preferences_about);
        Preference versionPreference = findPreference(getString(R.string.pref_about_version_key));
        versionPreference.setTitle(getString(R.string.pref_about_version_title, getSimpleVersion()));
        versionPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                clickCounter++;
                if (clickCounter > A_BUNCH_OF_CLICKS_TO_PREVENT_ACCIDENTAL_TRIGGERING) {
                    clickCounter = 0;
                    Toast.makeText(getActivity(), getVersion(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    private String getSimpleVersion() {
        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    @SuppressLint("DefaultLocale")
    private String getVersion() {
        final StringBuilder versionText = new StringBuilder();
        try {
            final PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            versionText.append(String.format("Version %s (%d)", packageInfo.versionName, packageInfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "Failed getting app version name.");
        }
        versionText.append("\nSync Engine ").append(ZmsVersion.ZMS_VERSION)
                   .append("\n - built with AVS ").append(ZmsVersion.AVS_VERSION)
                   .append("\nAVS ").append(getString(R.string.avs_version))
                   .append("\nAudio-notifications ").append(getString(R.string.audio_notifications_version));

        return versionText.toString();
    }

    public interface Container {
    }
}
