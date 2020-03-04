package com.vn.babumart.untils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vn.babumart.R;
import com.vn.babumart.config.Constants;
import com.vn.babumart.models.ObjLogin;
import com.vn.babumart.untils.SharedPrefs;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Envy 15T on 6/5/2015.
 */
public class StringUtil {

    /**
     * Upper case first letter in string
     *
     * @return
     */
    public static void onLunchAnotherApp(Context context) {

        final String appPackageName = "neo.vn.test365children";

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
        if (intent != null) {

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            onGoToAnotherInAppStore(intent, appPackageName, context);

        }
    }

    public static void call_phone(Context mContext, String phone) {
        sPhone = phone;
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(mContext, phone);
        } else {
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall(mContext, phone);
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS_STORAGE,
                        Constants.RequestCode.PERMISSION_CALL_PHONE);
            }
        }
    }

    public static boolean check_login_customer() {
        try {
            boolean isLoginCustomer = false;
            boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
            if (!isLogin) {
                return isLoginCustomer;
            } else {
                if (objLogin == null)
                    return isLoginCustomer;
                if (objLogin.getGROUPS().equals("8")) {
                    return isLoginCustomer;
                } else {
                    isLoginCustomer = true;
                    return isLoginCustomer;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean check_login_group() {
        try {
            boolean isLoginCustomer = false;
            boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_IS_LOGIN, Boolean.class);
            ObjLogin objLogin = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USER_LOGIN, ObjLogin.class);
            if (!isLogin) {
                return isLoginCustomer;
            } else {
                if (objLogin == null)
                    return isLoginCustomer;
                if (objLogin.getGROUPS() != null) {
                    isLoginCustomer = true;
                    return isLoginCustomer;
                } else {
                    return isLoginCustomer;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String sPhone;

    private static void phoneCall(Context mContext, String phone) {
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            mContext.startActivity(callIntent);
        } else {
            Toast.makeText(mContext, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void load_glide_image(String sUrl, Context context, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.img_defaul)
                .error(R.drawable.img_defaul);
        Glide.with(context).load(sUrl)
                .apply(options).into(imageView);
    }

    public static void share_app(Activity activity, String content) {
        final String my_package_name = "com.vn.f5sale";  // <- HERE YOUR PACKAGE NAME!!
        String url = "";
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
            // String sAux = "Tải Home365 tại https://home365.online để con làm bài tập về nhà, rất tuyệt vời.";
            url = "https://play.google.com/store/apps/details?id=" + my_package_name;
            // sAux = sAux + url + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, content);
            activity.startActivity(Intent.createChooser(i, activity.getResources().getString(R.string.app_name)));
        } catch (Exception e) {
            e.toString();
        }
    }


    public static String formatInteger(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat("VND" + "#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }


    public static void onGoToAnotherInAppStore(Intent intent, String appPackageName, Context context) {
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + appPackageName));
            context.startActivity(intent);

        } catch (android.content.ActivityNotFoundException anfe) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            context.startActivity(intent);

        }

    }

    public static String UppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    public static boolean check_tiengviet(String sInput) {
        if (sInput.matches("^[a-z A-Z 0-9]{1,50}$")) {
            return true;
        } else {
            return false;
        }
    }

    public static String convert_html(String content) {
        String s_resutl = "";
        if (content != null && content.length() > 0) {
            s_resutl = content.replaceAll("&#34;", "\"");
            s_resutl = s_resutl.replaceAll("&#92;", "\\\\");
        }
        return s_resutl;
    }

    public static String replace_all(String content) {
        String s_resutl = "";
        try {
            if (content.length() == 0)
                return s_resutl;
            s_resutl = content.replaceAll("VND", "")
                    .replaceAll("đ", "")
                    .replaceAll(",", "")
                    .replaceAll("\\.", "")
            ;
        } catch (Exception e) {

        }
        return s_resutl;
    }

    public static String format_point(float fPoint) {
        String sPoint = "" + fPoint;
        String[] sTest = sPoint.split("\\.");
        if (sTest[1].equals("0")) {
            int iPoint = (int) Math.round(fPoint);
            return "" + iPoint;
        } else {
            double f = Double.parseDouble("0." + sTest[1]);
            if (f > 0 && f < 0.15) {
                return sTest[0];
            } else if (f >= 0.15 && f < 0.35) {
                return sTest[0] + ".25";
            } else if (f >= 0.35 && f < 0.65) {
                return sTest[0] + ".5";
            } else if (f >= 0.65 && f < 0.85) {
                return sTest[0] + ".75";
            } else if (f >= 0.85 && f < 1) {
                return "" + (Integer.parseInt(sTest[0]) + 1);
            }
            return "";
        }
    }

    public static String formatNumber(String number) {
        String sMonney = "";
        if (number == null)
            return "";
        if (number.matches("[0-9]+") && number.length() > 2) {
            number = number.replaceAll(" ", "");
            number = number.replaceAll(",", "");
            int iNumber = Integer.parseInt(number);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            sMonney = (formatter.format(iNumber) + " VNĐ");
        }

        return sMonney;
    }

    public static String formatNumber_new(String number) {
        String sMonney = "";
        if (number == null)
            return "";
        if (number.matches("[0-9]+") && number.length() > 2) {
            number = number.replaceAll(" ", "");
            number = number.replaceAll(",", "");
            int iNumber = Integer.parseInt(number);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            sMonney = (formatter.format(iNumber));
        }

        return sMonney;
    }

    public static String conventMonney(String number) {
        String sMonney = "";
        if (number == null)
            return "";
        number = number.replaceAll(" ", "");
        number = number.replaceAll(",", "");
        number = number.replaceAll("\\.", "");
        if (number.matches("[0-9]+")) {
            //number = number.replaceAll(".", "");
            if (number.length() > 0) {
                int iNumber = Integer.parseInt(number);
                DecimalFormat formatter = new DecimalFormat("###,###,###,###");
                sMonney = (formatter.format(iNumber) + "đ");
            } else {
                sMonney = "";
            }
        }
        return sMonney;
    }

    public static String conventMonney_Long(String number) {
        String sMonney = "";
        if (number == null)
            return "";
        number = number.replaceAll(" ", "");
        number = number.replaceAll(",", "");
        number = number.replaceAll("\\.", "");
        if (number.matches("[0-9]+")) {
            //number = number.replaceAll(".", "");
            if (number.length() > 0) {
                long iNumber = Long.parseLong(number);
                DecimalFormat formatter = new DecimalFormat("###,###,###,###");
                sMonney = (formatter.format(iNumber) + "đ");
            } else {
                sMonney = "";
            }
        }
        return sMonney;
    }

    /* public static String removeAccent(String s) {

         String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
         Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
         return pattern.matcher(temp).replaceAll("");
     }*/
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d");
    }

    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {
        return year + "/" + monthOfYear + "/" + dayOfMonth;
    }

    public static String formatDateJP(int year, int monthOfYear, int dayOfMonth) {
        return year + "年" + monthOfYear + "月" + dayOfMonth + "日";
    }


    public static String formatDate(Calendar calendar) {
        return formatDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String formatDateJP(Calendar calendar) {
        return formatDateJP(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static boolean checkStringValid(String data) {
        if (data != null && !data.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkStringNull(String data) {
        if (data != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkZero(String data) {
        if (checkStringValid(data) && !data.equals("0"))
            return true;
        else
            return false;
    }

    public static void displayText(String text, TextView textView) {
        if (textView == null)
            return;
        if (text != null && !text.isEmpty() && !text.equals("null")) {
            textView.setText(text);
        } else {
            textView.setText("");
        }

    }

    public static boolean isPhoneNumber(String receiver) {
        boolean ok = true;
        if (receiver.length() > 12
                || receiver.length() < 9
                || (receiver.length() == 9 && !(receiver.startsWith("9") || receiver.startsWith("89") || receiver.startsWith("88") || receiver.startsWith("86")))
                || (receiver.length() == 10 && !(receiver.startsWith("09") || receiver.startsWith("089") || receiver.startsWith("088") || receiver.startsWith("086") || receiver.startsWith("1")))
                || (receiver.length() == 11 && !(receiver.startsWith("849") || receiver.startsWith("8489") || receiver.startsWith("8488") || receiver.startsWith("8486") || receiver.startsWith("01")))
                || (receiver.length() == 12 && !receiver.startsWith("841"))) {
            ok = false;
        } else {
            for (int i = 0; i < receiver.length(); i++) {
                char c = receiver.charAt(i);
                if ((c > '9') || (c < '0')) {
                    ok = false;
                    break;
                }
            }
        }
        return ok;
    }

    public static void displayText(String text, TextView textView, String prefix) {
        if (textView == null)
            return;
        if (text != null && !text.isEmpty() && !text.equals("null")) {
            textView.setText(text + prefix);
        } else {
            textView.setText("0" + prefix);
        }

    }

    public static void displayText(Float text, TextView textView) {
        if (text != null) {
            String.format("%.0f", text);
        } else {
            textView.setText("");
        }
    }

    public static void displayText(int text, TextView textView) {
        if (text > 0) {
            textView.setText(text + "");
        } else {
            textView.setText("");
        }
    }

    /**
     * Check valid email
     *
     * @param target
     * @return true if valid email, false if invalid email
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean validatePhoneNumber(String s) {
        if (s == null)
            return false;
        for (char c : s.toCharArray())
            if (c < '0' || c > '9')
                return false;
        return true;
    }

    public static DecimalFormat setDecimalFormat() {
        Locale locale = new Locale("en", "UK");
        String pattern = "#,###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);
        return decimalFormat;
    }

    public static void fillPrefix(TextView tv, float value, String prefix) {
        if (value == 0) {
            tv.setText(prefix + "0.00");
        } else if (value > 0) {
            if (!convertTextFloattoInt(String.valueOf(value))) {
                tv.setText(prefix + setDecimalFormat().format(value));
            } else {
                tv.setText(prefix + setDecimalFormat().format(value));
            }
        } else {
            tv.setText("");
        }
    }

    public static void fillSuffixes(TextView tv, float value, String suffixes) {
        if (value == 0) {
            tv.setText("0.00" + suffixes);
        } else if (value > 0) {
            if (!convertTextFloattoInt(String.valueOf(value))) {
                tv.setText(setDecimalFormat().format(value) + suffixes);
            } else {
                tv.setText(setDecimalFormat().format(value) + suffixes);
            }
        } else {
            tv.setText("");
        }
    }

    /**
     * Ellipsize string with maxCharacter
     *
     * @param input
     * @param maxCharacters
     * @return
     */
    public static String ellipsize(String input, int maxCharacters) {
        if (maxCharacters < 3) {
            throw new IllegalArgumentException("maxCharacters must be at least 3 because the ellipsis already take up 3 characters");
        }
        if (input == null || input.length() < maxCharacters) {
            return input;
        }
        return input.substring(0, maxCharacters - 3) + "...";
    }

    public static boolean checkTypeFloat(String value) {
        if (value.contains(".")) {
            return true;
        }
        return false;
    }

    public static boolean convertTextFloattoInt(String value) {
        String result5[] = value.split("[.]");
        int b = Integer.parseInt(result5[1]);
        if (b > 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String input) {
        if (input == null || input.length() == 0) {
            return true;
        }
        return false;
    }

    public static String addString(String text) {
        return String.format("     %s     %s     %s", text, text, text);
    }

    public static String messageRegister(String text1, String text2) {
        return String.format("%s <font color='#ff5000'>%s</font>", text1, text2);

    }

    public static boolean checkFormatDate(String input) {
        return input.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");
    }

    public static <T> T[] appendArrString(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    //Lấy time hiện tại
    public static String get_current_time() {
        Calendar cal;
        SimpleDateFormat dft = null;
        String date = "";
        //Set ngày giờ hiện tại khi mới chạy lần đầu
        cal = Calendar.getInstance();
        //Định dạng kiểu ngày / tháng /năm
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date = dft.format(cal.getTime());
        //hiển thị lên giao diện
        return date;
    }

    public static void initWebview(String sData, WebView webView) {
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings();
        webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 2.5));
        //  webView.loadUrl(sUrl);
        webView.loadDataWithBaseURL("", sData,
                "text/html", "UTF-8", "");
    }
}
