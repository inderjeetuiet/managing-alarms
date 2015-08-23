package inderjeet.com.managingalarms.properties;

/**
 * Created by isingh on 8/6/15.
 */
public class wifiProperty
{
    public String ssid = null;
    public String bssid = null;
    public String capabilities = null;
    public int dbm;
    public int freq;

    private wifiProperty(int dbm, String ssid, String bssid, int freq, String capabilities)
    {
        this.dbm = dbm;
        this.ssid = ssid;
        this.bssid = bssid;
        this.freq = freq;
        this.capabilities = capabilities;
    }

    public static wifiProperty updateAllWifiData(int dbm, String ssid, String bssid, int freq, String capabilities){
        return new wifiProperty(dbm, ssid, bssid, freq, capabilities);
    }
}
