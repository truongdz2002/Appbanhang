package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import Fragment.Fragment_All_Product_In_Cart;
import Fragment.Fragment_Discout_In_Cart;

public class Adapter_load_tablayout_in_cart extends FragmentStateAdapter {
    public Adapter_load_tablayout_in_cart(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public Adapter_load_tablayout_in_cart(@NonNull Fragment fragment) {
        super(fragment);
    }

    public Adapter_load_tablayout_in_cart(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_All_Product_In_Cart();
            case 1:
                return new Fragment_Discout_In_Cart();
            default:return  new Fragment_All_Product_In_Cart();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
