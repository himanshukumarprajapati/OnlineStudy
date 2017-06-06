package regatta.com.oea;

/**
 * Created by rahul.singh on 5/1/2017.
 */



public class AppConfig {
    // Server user login url

    public static String URL_LOGIN = "http://192.168.0.102/android_login_api/login.php";
    //public static  String URL_LOGIN ="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=user_login&mailId=admin@gmail.com&password=e6e061838856bf47e1de730719fb2609";

    // Server user register url
    //public static String URL_REGISTER = "http://192.168.0.102/android_login_api/register.php";
    public static String URL_UPDATE ="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=update_user";
    public static String URL_REGISTER = "http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=manage_user&type=Add&firstName=rahul&lastName=singh&mailId=rahul.singh@companynonstop.com&contactNumber=2323233&password=111111";
    public static String URL_HISTORY ="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=get_order_history&userId=1";
    public static String URL_PAYMENT ="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=manage_order&customerName=abc&customerMailId=abc@gmail.com&customerMobileNumber=7848375&itemName=test1&itemDescription=tsdtyd&totalPrice=20";
    public static String BaseURL="http://139.59.43.252/";
    public static String BaseURLL="http://162.144.82.185/";
    public static String URL=BaseURL+"mpos/api/ws/controller/index.php?";
    public static String TAGG="MY_Log";
    public static String TAG="MY_Log";
    public static String TAG1="MY_Log";
    public static String TAG11="MY_Log";
    public static String URL_PAYMENTT ="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=manage_user";

    //for update user api
    public static String URL_BANKDETAIL="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=get_bank_details&userId=1";
    public static String URL_get_organisation_type_list="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=get_organisation_type_list";
    public static String URL_Updateuser="http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=update_user";
    //public static String Updates=http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=update_user&id=1&firstName=abc&lastName=abc&mailId=abc@gmail.com&contactNumber=1234567890;
}
