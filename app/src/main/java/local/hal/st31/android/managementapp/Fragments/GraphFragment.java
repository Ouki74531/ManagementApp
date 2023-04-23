package local.hal.st31.android.managementapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import local.hal.st31.android.managementapp.R;
import local.hal.st31.android.managementapp.databinding.FragmentGraphBinding;
import local.hal.st31.android.managementapp.models.Graphs;
import local.hal.st31.android.managementapp.models.Tasks;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphFragment#} factory method to
 * create an instance of this fragment.
 */
public class GraphFragment extends Fragment {

    public GraphFragment() {

    }
    FragmentGraphBinding binding;
    ArrayList<Tasks> list = new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//データベースにデータを取得してkる
        // Inflate the layout for this fragment


        binding = FragmentGraphBinding.inflate(inflater,container, false);
        database = FirebaseDatabase.getInstance();//データベースを使用可能に
        //grid背景色
        binding.barChart.setDrawGridBackground(true);
        //no description text
        binding.barChart.getDescription().setEnabled(true);

        //表示データ取得
        BarData data = new BarData(getBarData());
        binding.barChart.setData(data);

        //Y軸(左)
        YAxis left = binding.barChart.getAxisLeft();
        left.setAxisMinimum(0);
        left.setAxisMaximum(100);
        left.setLabelCount(5);
        left.setDrawTopYLabelEntry(true);


        //Y軸(右)
        YAxis right = binding.barChart.getAxisRight();
        right.setDrawLabels(false);
        right.setDrawGridLines(false);
        right.setDrawZeroLine(true);
        right.setDrawTopYLabelEntry(true);

        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //グラフ上の表示
        binding.barChart.setDrawValueAboveBar(true);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.setClickable(false);

        //凡例
        binding.barChart.getLegend().setEnabled(false);

        binding.barChart.setScaleEnabled(false);
        binding.barChart.getAxisLeft().setEnabled(false);                           // 右側は非表示
        binding.barChart.getAxisRight().setEnabled(false);                          // 右側は非表示
        binding.barChart.getXAxis().setTextSize(12);                                // テキストサイズ
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);        // 下側に表示
        binding.barChart.getXAxis().setYOffset(-3);                                 // 上方向にオフセット
        binding.barChart.getXAxis().setDrawGridLines(false);                        // グリッド線なし
        binding.barChart.getXAxis().setDrawAxisLine(false);                         // 枠線なし


        database.getReference().child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();
                labels.add("");
                list.clear();
                int i = 1;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tasks tasks = dataSnapshot.getValue(Tasks.class);
                    entries.add(new BarEntry(i, Integer.parseInt(tasks.getValue())));
                    labels.add(tasks.getName());
                    i += 1;
                }
                List<IBarDataSet> bars = new ArrayList<>();
                BarDataSet dataSet = new BarDataSet(entries, "bar");
                dataSet.setValueTextSize(12);
                //ハイライトさせない
                dataSet.setHighlightEnabled(false);
                binding.barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                bars.add(dataSet);
                BarData data = new BarData(bars);
                binding.barChart.getXAxis().setEnabled(true);
                binding.barChart.setData(data);
                binding.barChart.setVisibility(View.VISIBLE);
                binding.barChart.notifyDataSetChanged();
                System.out.println(labels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return binding.getRoot();
    }

    //棒グラフのデータを取得
    private List<IBarDataSet> getBarData(){
        //表示させるデータ
        database.getReference().child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tasks tasks = dataSnapshot.getValue(Tasks.class);
                    list.add(tasks);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<IBarDataSet> bars = new ArrayList<>();
        BarDataSet dataSet = new BarDataSet(entries, "bar");

        //ハイライトさせない
        dataSet.setHighlightEnabled(false);

        bars.add(dataSet);

        return bars;
    }
}