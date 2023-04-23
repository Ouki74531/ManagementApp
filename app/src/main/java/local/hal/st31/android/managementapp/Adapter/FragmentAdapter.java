package local.hal.st31.android.managementapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import local.hal.st31.android.managementapp.Fragments.ChatFragment;
import local.hal.st31.android.managementapp.Fragments.GraphFragment;
import local.hal.st31.android.managementapp.Fragments.GraphListFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//どのフラグメントが実行されるか
        switch (position){
            case 0:
                return new ChatFragment();
            case 1:
                return new GraphFragment();
            case 2:
                return new GraphListFragment();
            default:
                return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {//遷移したページごとにページに表示するタイトルを決める。
        String title = null;
        if (position == 0){
            title = "チャット";
        }if (position == 1){
            title = "進捗グラフ";
        }if (position == 2){
            title = "タスク";
        }
        return title;
    }
}
