package cityxpress.cbt.com.cityxpress.activitys.Data;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

import cityxpress.cbt.com.cityxpress.activitys.model.LatLongModel;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainDedatils;
import cityxpress.cbt.com.cityxpress.activitys.model.TrainRoutModel;

/**
 * Created by admin on 8/10/2018.
 */

public class MyApplication {
    public static String BaseUrl;
    public static String URL_AddBusiness = "http://www.cityxpress.in/api/BusinessApi/AddBusiness";
    public static String URL_GetCAtegory = "http://www.cityxpress.in/api/BusinessApi/GetCategory/";
    public static String URL_GetSubCAtegory = "http://www.cityxpress.in/api/BusinessApi/GetSubCategory?categoryID";
    public static String URL_GetState = "http://www.cityxpress.in/api/BusinessApi/GetState/";
    public static String URL_GetCity = "http://www.cityxpress.in/api/BusinessApi/GetCityByStateID/?stateID=";
    public static String URL_GetCityArea = "http://www.cityxpress.in/api/BusinessApi/GetnCityAreaByCityID?cityID=";
    public static String URL_GetServices = "http://www.cityxpress.in/api/BusinessApi/GetServiceBySubCategoryID?subCategoryID=";
    public static String URL_Amount = "http://www.cityxpress.in/api/BusinessApi/GetnAmountByCityAreaID?cityAreaID=";
    public static String URL_GetPincode = "http://www.cityxpress.in/api/BusinessApi/GetPinCodeByCityAreaID?cityAreaID=";

    public static String URL_AddEvent = "http://www.cityxpress.in/api/EventApi/AddEvent";
    public static String URL_GetBusinessDetails = "http://cityxpress.in/api/BusinessApi/GetBusinessByBusinessID?businessRegistrationID=";

    public static String URL_GetEventList = "http://www.cityxpress.in/api/EventApi/GetEventList";
    public static String URL_Login = "http://www.cityxpress.in/api/UserApi/Login?phoneNumber=";
    public static String URL_GetBanner = "http://www.cityxpress.in/api/CommonApi/GetBannerListByCity?cityID=";
    public static String URL_MyCityMenu = "http://www.cityxpress.in/api/BusinessApi/GetCategory/";
    public static String URL_MyCitySubMenu = "http://www.cityxpress.in/api/BusinessApi/GetSubCategory?categoryID=";
    public static String URL_MyCitySubSubMenu = "http://www.cityxpress.in/api/BusinessApi/GetBusiness?subCategoryID=";
    public static String URL_Registration = "http://www.cityxpress.in/api/UserApi/Register";

    public static String URL_SewarchTrain = "https://api.railwayapi.com/v2/suggest-station/name/";





    public static String api = "/apikey/c9it1eodjl/";


    public static String accesstoken;
    public static String phonenumber;
    public static String name;
    public static String UserID;
    public static String CityID;
    public static String Cityname;
    public static String fromstnsave;
    public static String tostnsave;
    public static String fromstr;
    public static String tostr;
    public static String fromstationname;
    public static String tostationname;

    public static String mycitysubcategoruselectedid;

    public static TrainDedatils selectedTrain;

    public static String STrainNum;
    public static String STrainName;
    public static String STrainDeparts;
    public static String STrainArrives;
    public static String Ssourcelat;
    public static String Ssourcelon;
    public static String Sdestnlat;
    public static String Sdestnlog;
    public static String mycitysubcategoruselectedtitle;

    public static String subcat_select_id;
    public static String subcat_select_name;


    public static String subcat_bus_id;
    public static String subcat_bus_name;
    public static String bussinessDetails_Id;
    public static String SelectEvent_Id;
    public static ArrayList<LatLongModel> latlonglist;

    public static NotificationCompat.Builder builder;
    public static NotificationManager nManager;
    public static ArrayList<TrainRoutModel> rtlist;
    public static int fromindex;
    public static int toindex;
    public static ArrayList<String> stationArrayList = new ArrayList<>();
    public static String fromid;
    public static String toid;
    public static  ArrayList<String> fevList=new ArrayList<>();
    public static String selectedLocalSource;
    public static ArrayList<String> allStation;
    public static int fevId;
    public static int selectedStaion;
    public static String selectedStaionFrom="";
    public static String selectedStaionTo="";
    public static ArrayList<String> allExpStation;
    public static int allStationSource;
    public static String selectedSorceStation="";
    public static String selectedDestStation="";






  /*  public static String
    public static String
    public static String
    public static String

    public static String
    public static String*/


}
