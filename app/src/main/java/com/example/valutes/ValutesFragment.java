package com.example.valutes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ValutesFragment extends Fragment {
    AdapterValute adapterValute;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Country> countries=new ArrayList<>();
    Handler handler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetData getData=new GetData();
        getData.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.valutesfragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.Valutes);
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.Swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.removeAllViews();
                countries.clear();
                GetData getData=new GetData();
                getData.start();
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                adapterValute=new AdapterValute(MainActivity.getinstance(),countries);
                recyclerView.setAdapter(adapterValute);
            }
        };
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class GetData extends Thread{
        @Override
        public void run() {
            super.run();
            String result="";
            String flags="";
            try {
                URL url1 = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
                URL url2 = new URL("https://gist.github.com/sanchezzzhak/8606e9607396fb5f8216/raw/8a7209a4c1f4728314ef4208abc78be6e9fd5a2f/ISO3166_RU.json");
                Scanner scanner=new Scanner((InputStream) url1.openStream());
                while(scanner.hasNext()) {
                    result += scanner.nextLine();
                }
                scanner.close();
                scanner = new Scanner((InputStream) url2.openStream());
                while(scanner.hasNext()){
                    flags+=scanner.nextLine();
                }
                scanner.close();
                System.out.println(flags);

            }catch (MalformedURLException e){
                e.getStackTrace();
            }catch (IOException e){
                e.getStackTrace();
            }catch (NetworkOnMainThreadException e){
                System.out.println("EEEEEEEEE: "+e);
            }
            try {
                JSONObject json =new JSONObject(result).getJSONObject("Valute");
                JSONArray jsonflags = new JSONArray((flags));
                for(int i=0;i<json.names().length();i++){
                    JSONObject jsoncountry = json.getJSONObject(json.names().getString(i));
                    URL urlbit;
                    Bitmap bitmap = null;
                    System.out.println(jsonflags.length());
                    for(int j=0;j<jsonflags.length();j++){
                        System.out.println(jsoncountry.getString("CharCode").substring(0,2)+"/"+jsonflags.getJSONObject(j).getString("iso_code2"));
                        if(jsoncountry.getString("CharCode").substring(0,2).compareTo(jsonflags.getJSONObject(j).getString("iso_code2"))==0){
                            System.out.println("PPPPPP: "+jsoncountry.getString("CharCode").substring(0,2)+"/"+jsonflags.getJSONObject(j).getString("iso_code2"));
                            System.out.println(jsonflags.getJSONObject(j).getString("name_ru"));
                            urlbit=new URL("https:"+jsonflags.getJSONObject(j).getString("flag_url"));
                            bitmap = BitmapFactory.decodeStream((InputStream) urlbit.openStream());
                            break;
                        }
                    }
                    countries.add(new Country(jsoncountry.getString("Value"),jsoncountry.getString("Name"),bitmap));
                }


            } catch (JSONException | MalformedURLException jsonException) {
                jsonException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            handler.sendEmptyMessage(1);

        }


    }
}
