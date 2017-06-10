package myuitest.someday.cn.myuitest;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TestMutileRecycleViewActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mutile_recycle_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Log.d("adsfasdfa","asdfasdfasd");

        final RecyclerView view = (RecyclerView) findViewById(R.id.recyclerView);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setHasFixedSize(true);
        view.setItemViewCacheSize(9);
        view.setAdapter(new RecyclerView.Adapter()
        {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                return new ItemViewHolder(parent);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
            {

            }

            @Override
            public int getItemCount()
            {
                return 9;
            }
        });



        Log.d("adsfasdfa","asdfasdfasd");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("adsfasdfa","asdfasdfasd");
                view.getAdapter().notifyDataSetChanged();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }, 1000*10);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("adsfasdfa","asdfasdfasd");
                view.getAdapter().notifyDataSetChanged();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }, 1000*20);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.getAdapter().notifyDataSetChanged();
                Toast.makeText(TestMutileRecycleViewActivity.this,"10",Toast.LENGTH_SHORT);
            }
        }, 1000*30);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.getAdapter().notifyDataSetChanged();
                Toast.makeText(TestMutileRecycleViewActivity.this,"10",Toast.LENGTH_SHORT);
            }
        }, 1000*40);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.getAdapter().notifyDataSetChanged();
                Toast.makeText(TestMutileRecycleViewActivity.this,"10",Toast.LENGTH_SHORT);
            }
        }, 1000*50);
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        RecyclerView recyclerViewLeft;
        RecyclerView recyclerViewRight;
        public ItemViewHolder(View itemView) {
            super(LayoutInflater.from(itemView.getContext()).inflate(R.layout.content_test_mutile_recycle_item, (ViewGroup) itemView,false));
            recyclerViewLeft = (RecyclerView) this.itemView.findViewById(R.id.recyclerViewLeft);
            recyclerViewRight = (RecyclerView) this.itemView.findViewById(R.id.recyclerViewRight);
            recyclerViewLeft.setLayoutManager(new GridLayoutManager(this.itemView.getContext(),6,GridLayoutManager.HORIZONTAL,false));
            recyclerViewLeft.setHasFixedSize(true);
            recyclerViewRight.setLayoutManager(new GridLayoutManager(this.itemView.getContext(),6,GridLayoutManager.HORIZONTAL,false));
            recyclerViewRight.setHasFixedSize(true);
            RecyclerView.Adapter adapter = new RecyclerView.Adapter()
            {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
                {
                    return new Item2ViewHolder(parent);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
                {
                    ((Item2ViewHolder)holder).bind(position);
                }

                @Override
                public int getItemCount()
                {
                    return 90;
                }

                class Item2ViewHolder extends RecyclerView.ViewHolder
                {
                    private final TextView textview;

                    public Item2ViewHolder(View itemView) {
                        super(LayoutInflater.from(itemView.getContext()).inflate(R.layout.content_test_mutile_recycle_item_item, (ViewGroup) itemView,false));
                        textview = (TextView)this.itemView.findViewById(R.id.text);
                    }

                    public void bind(int i)
                    {
                        textview.setText(String.valueOf(i
                        ));
                    }
                }

            };
            recyclerViewLeft.setAdapter(adapter);
            recyclerViewRight.setAdapter(adapter);



        }
    }


}
