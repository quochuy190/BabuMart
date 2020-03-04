package com.vn.babumart.untils;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vn.babumart.R;
public class FragmentUtil {

    public static void popBackStack(FragmentActivity activity) {
        if (activity == null) {
            return;
        }
        activity.getSupportFragmentManager().popBackStack();
    }

    public static void clearAllBackStack(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
      //  fm.executePendingTransactions();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    /**
     * @param fragmentManager
     * @param fragment
     * @param data
     */

    public static void pushFragment(FragmentManager fragmentManager, int layout, @NonNull Fragment fragment, @Nullable Bundle data) {
        //  DebugLog.e("bundle data:" + data);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null && fragment.isAdded()) {
            fragmentTransaction.remove(fragment);    // detach
        }
        fragmentTransaction.replace(layout, fragment);//R.id.content_frame is the layout you want to replace
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }


    /**
     * @param fragmentManager
     * @param fragment
     * @param data
     */
    public static void replaceFragment(FragmentManager fragmentManager, @NonNull Fragment fragment,
                                       @Nullable Bundle data) {
        showFragment(fragmentManager, fragment, false, data, null, false);
    }

    /**
     * Push fragment to container (with add backstack)
     *
     * @param activity
     * @param fragment
     * @param data
     */
    public static void pushFragment(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        //   DebugLog.e("bundle data:" + data);
        showFragment(activity, fragment, true, data, null, false);
    }

    public static void pushFragmentAnimationRightToLeft(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        //   DebugLog.e("bundle data:" + data);
        showFragment(activity, fragment, true, data, null, true);
    }


    public static void pushFragmentAnimationLeftToRight(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        //    DebugLog.e("bundle data:" + data);
        showFragmentLeftToRight(activity, fragment, true, data, null, true);
    }


    /**
     * Replace fragment in container (without add backstack)
     *
     * @param activity
     * @param fragment
     * @param data
     */
    public static void replaceFragment(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        showFragment(activity, fragment, false, data, null, false);
    }

    /**
     * Push fragment to container (with add backstack) with animation
     *
     * @param activity
     * @param fragment
     * @param data
     */
    public static void pushFragmentWithAnimation(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        //    DebugLog.e("bundle data:" + data);
        showFragment(activity, fragment, true, data, null, true);
    }

    /**
     * Replace fragment in container (without add backstack) with animation
     *
     * @param activity
     * @param fragment
     * @param data
     */
    public static void replaceFragmentAnimation(FragmentActivity activity, @NonNull Fragment fragment, @Nullable Bundle data) {
        showFragment(activity, fragment, false, data, null, true);
    }

    /**
     * @param activity
     * @param fragment
     * @param isPushInsteadOfReplace
     * @param data
     * @param tag
     * @param isShowAnimation
     */


    public static void showFragment(FragmentActivity activity, @NonNull Fragment fragment,
                                    boolean isPushInsteadOfReplace, @Nullable Bundle data,
                                    @Nullable String tag, boolean isShowAnimation) {
        if (activity == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (isShowAnimation) {
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        fragmentTransaction.replace(R.id.frame_home_fragment, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(null);
        }

        if (isShowAnimation) {
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        } else {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void showFragmentLeftToRight(FragmentActivity activity, @NonNull Fragment fragment, boolean isPushInsteadOfReplace, @Nullable Bundle data, @Nullable String tag, boolean isShowAnimation) {
        if (activity == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        if (isShowAnimation) {
//            fragmentTransaction.setCustomAnimations(R.anim.slide_out_right, R.anim.slide_in_left);

        }

        fragmentTransaction.replace(R.id.frame_home_fragment, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(null);
        }

        if (isShowAnimation) {
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        } else {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }


    public static void showFragment(FragmentManager fragmentManager, @NonNull Fragment fragment,
                                    boolean isPushInsteadOfReplace, @Nullable Bundle data,
                                    @Nullable String tag, boolean isShowAnimation) {
        if (fragmentManager == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isShowAnimation) {
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,
//                    R.anim.slide_out_up, R.anim.slide_in_up, R.anim.slide_out_up);
        }

        fragmentTransaction.replace(R.id.frame_home_fragment, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
    }


    public static void addFragment(FragmentActivity activity, @NonNull Fragment fragment, boolean isAddToBackStack) {
        if (activity == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded())
            fragmentTransaction.add(R.id.frame_home_fragment, fragment, null);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void addFragmentHome(FragmentActivity activity, @NonNull Fragment fragment, boolean isAddToBackStack) {
        if (activity == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.frame_home_fragment, fragment, null);

        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void addFragmentData(FragmentActivity activity, @NonNull Fragment fragment,
                                       boolean isAddToBackStack, Bundle bundle) {
        if (activity == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_home_fragment, fragment, null);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        //       fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // fragmentTransaction.commitAllowingStateLoss();
        fragmentTransaction.commit();
    }

    public static void showFragmentWithAnimation(FragmentActivity activity, @NonNull Fragment fragment,
                                                 boolean isPushInsteadOfReplace, @Nullable Bundle data,
                                                 @Nullable String tag, int enterAnim, int exitAnim) {
        if (activity == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(enterAnim,
                exitAnim, enterAnim, exitAnim);

        fragmentTransaction.replace(R.id.frame_home_fragment, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commitAllowingStateLoss();
    }


    public static void pushFragmentLayoutMain(FragmentManager manager, int layout, Fragment fragment, String TAG) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(layout, fragment, TAG);//R.id.content_frame is the layout you want to replace
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(TAG);
    }

    public static void replaceFragment(FragmentManager manager, int layout, Fragment fragment, String Back_TAG, String TAG) {

        boolean fragmentPopped = manager.popBackStackImmediate(Back_TAG, 0);
        if (!fragmentPopped) {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(layout, fragment, TAG);//R.id.content_frame is the layout you want to replace
            fragmentTransaction.addToBackStack(Back_TAG);
            fragmentTransaction.commit();
        }
    }
}
