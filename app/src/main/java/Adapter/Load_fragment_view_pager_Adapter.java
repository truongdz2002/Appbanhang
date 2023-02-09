package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import Fragment.Fragment_Home;
import Fragment.Fragment_Endow;
import Fragment.Fragment_Information;
import Fragment.Fragment_Notification;
import Fragment.Fragment_History_Transaction;

public class Load_fragment_view_pager_Adapter extends FragmentStateAdapter {
    public Load_fragment_view_pager_Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public Load_fragment_view_pager_Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public Load_fragment_view_pager_Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_Home();
            case 1:
                return  new Fragment_Endow();
            case 2:
                return new Fragment_History_Transaction();
            case 3:
                return new Fragment_Notification();
            case 4:
                return new Fragment_Information();
            default:
                return new Fragment_Home();
        }
    }

    @Override
    public int getItemCount()
    {
        return 5;
    }
}
