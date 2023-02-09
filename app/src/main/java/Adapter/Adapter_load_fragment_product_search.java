package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import Fragment.Fragment_Price;
import Fragment.Fragment_Selling;
import Fragment.Fragment_Latest;
import Fragment.Fragment_Relate_To;

public class Adapter_load_fragment_product_search extends FragmentStateAdapter {
    public Adapter_load_fragment_product_search(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public Adapter_load_fragment_product_search(@NonNull Fragment fragment) {
        super(fragment);
    }

    public Adapter_load_fragment_product_search(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_Relate_To();
            case 1:
                return new Fragment_Latest();
            case 2:
                return new Fragment_Selling();
            case 3:
                return new Fragment_Price();
            default:return  new Fragment_Relate_To();
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
