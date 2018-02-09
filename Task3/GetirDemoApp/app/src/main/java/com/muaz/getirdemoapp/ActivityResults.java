package com.muaz.getirdemoapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muaz.getirdemoapp.models.RequestObj;
import com.muaz.getirdemoapp.models.objResponse;
import com.muaz.getirdemoapp.network.GetirService;
import com.muaz.getirdemoapp.network.NetworkAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityResults extends AppCompatActivity {

    GetirService getirService = NetworkAPI.getInstance();

    objResponse responseList;

    int currentPage;
    int totalPage;

    @BindView(R.id.et_next_page)
    TextView nextPage;
    @BindView(R.id.et_page_prev)
    TextView prevPage;
    @BindView(R.id.et_page_num)
    TextView pageNum;
    @BindView(R.id.ll_result_container)
    LinearLayout resultContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_table);
        ButterKnife.bind(this);
        currentPage = 1;
        totalPage = 1;

        if(getIntent().getExtras() != null){
            requestItems((RequestObj) getIntent().getExtras().getSerializable("requestObject"));
        }
    }



    private void requestItems(RequestObj req){
         Call<objResponse> search = getirService.getSearchList(req);

        final ProgressDialog dialog = ProgressDialog.show(ActivityResults.this, "",
                "Searching for results", true);
        search.enqueue(new Callback<objResponse>() {
            @Override
            public void onResponse(Call<objResponse> call, Response<objResponse> response) {
                dialog.dismiss();
                setItems(response.body());
            }

            @Override
            public void onFailure(Call<objResponse> call, Throwable t) {

            }
        });
    }


    private void setItems(objResponse response){
        responseList = response;
        totalPage = responseList.getRecords().size()/10 + (responseList.getRecords().size()%10 == 0 ? 0 : 1);
        pageNum.setText(currentPage+"/"+totalPage);

        fillTableItems();
    }

    private void fillTableItems(){
        if(totalPage == currentPage){
            int quo = responseList.getRecords().size()%10;
            int boundary = (currentPage-1)*10;
            for(int i = boundary; i < boundary + quo ; i++ ){
                View view = resultContainer.getChildAt(i%10);
                view.setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.tv_item_id)).setText(responseList.getRecords().get(i).get_id().get_id());
                ((TextView) view.findViewById(R.id.tv_item_key)).setText(responseList.getRecords().get(i).get_id().getKey());
                ((TextView) view.findViewById(R.id.tv_item_value)).setText(responseList.getRecords().get(i).get_id().getValue());
                ((TextView) view.findViewById(R.id.tv_item_creation)).setText(responseList.getRecords().get(i).get_id().getCreatedAt());
                ((TextView) view.findViewById(R.id.tv_item_count)).setText(responseList.getRecords().get(i).getTotalCount()+"");
            }

            for(int i = quo ; i < 10 ; i++ ){
                View view = resultContainer.getChildAt(i);
                view.setVisibility(View.GONE);
            }
        }else{
            int boundary = (currentPage-1)*10;
            for(int i = boundary; i < boundary + 10 ; i++ ){
                View view = resultContainer.getChildAt(i%10);
                view.setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.tv_item_id)).setText(responseList.getRecords().get(i).get_id().get_id());
                ((TextView) view.findViewById(R.id.tv_item_key)).setText(responseList.getRecords().get(i).get_id().getKey());
                ((TextView) view.findViewById(R.id.tv_item_value)).setText(responseList.getRecords().get(i).get_id().getValue());
                ((TextView) view.findViewById(R.id.tv_item_creation)).setText(responseList.getRecords().get(i).get_id().getCreatedAt());
                ((TextView) view.findViewById(R.id.tv_item_count)).setText(responseList.getRecords().get(i).getTotalCount()+"");
            }
        }
    }


    @OnClick(R.id.et_next_page)
    public void btnNextClicked(){

        if(currentPage == totalPage) return;

        currentPage++;
        pageNum.setText(currentPage+"/"+totalPage);
        fillTableItems();

    }

    @OnClick(R.id.et_page_prev)
    public void btnPrevClicked(){
        if(currentPage == 1) return;

        currentPage--;
        pageNum.setText(currentPage+"/"+totalPage);
        fillTableItems();
    }
}
