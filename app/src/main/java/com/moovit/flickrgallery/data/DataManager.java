

package com.moovit.flickrgallery.data;

import com.moovit.flickrgallery.data.db.DbHelper;
import com.moovit.flickrgallery.data.network.ApiHelper;
import com.moovit.flickrgallery.data.prefs.PreferencesHelper;


public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

}
