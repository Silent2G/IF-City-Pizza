package com.yleaf.stas.if_citypizza.admin.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yleaf.stas.if_citypizza.R;
import com.yleaf.stas.if_citypizza.Resource;
import com.yleaf.stas.if_citypizza.model.Pizza;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivityAdmin extends AppCompatActivity {

    private static Elements elements;
    private static final String TAG = MainActivityAdmin.class.getSimpleName();

    private static Button getDataBtn;
    private static Button pushDataBtn;

    private AtomicInteger atomicInteger;
    private int countSuccess = 0;

    private ImageView imagePizzaGetData;
    private static ImageView imagePizzaPushData;
    private static ImageView imagePizzaDownload;
    private static ImageView imagePizzaPizza;
    private ImageView imagePizzaMan;

    private static Animation animationTrance;
    private static Animation animationRotate;

    private static ArrayList<Pizza> aztecaList;
    private static ArrayList<Pizza> pizzaIFList;
    private static ArrayList<Pizza> camelotFoodList;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_admin);

        // init lists
        aztecaList = new ArrayList<Pizza>();
        pizzaIFList = new ArrayList<Pizza>();
        camelotFoodList = new ArrayList<Pizza>();

        // init widgets
        getDataBtn = findViewById(R.id.get_data_btn);
        pushDataBtn = findViewById(R.id.push_data_btn);

        pushDataBtn.setEnabled(false);

        imagePizzaGetData = findViewById(R.id.image_pizza_get_data);
        imagePizzaPushData = findViewById(R.id.image_pizza_push_data);
        imagePizzaDownload = findViewById(R.id.image_pizza_download);
        imagePizzaPizza = findViewById(R.id.image_pizza_pizza);
        imagePizzaMan = findViewById(R.id.image_pizza_man);

        imagePizzaPushData.setVisibility(View.INVISIBLE);
        imagePizzaDownload.setVisibility(View.INVISIBLE);
        imagePizzaPizza.setVisibility(View.INVISIBLE);
        imagePizzaMan.setVisibility(View.INVISIBLE);

        animationTrance = AnimationUtils.loadAnimation(this,R.anim.trance);
        animationRotate = AnimationUtils.loadAnimation(this,R.anim.rotate);

        //firstAnimation
        getDataAnimationStart();

        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AztecaPizza().execute();
                new PizzaIF().execute();
                new CamelotFood().execute();

                // animation
                getDataAnimationStop();
                downloadAnimationStart();
            }

        });

        pushDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                atomicInteger = new AtomicInteger();
                makeSuccessNumber();

                addDataToFireStore(aztecaList, Resource.AZTECA);
                addDataToFireStore(pizzaIFList, Resource.PIZZAIF);
                addDataToFireStore(camelotFoodList, Resource.CAMELOTFOOD);

                // animation
                pushDataAnimationStop();
                downloadAnimationStart();
                imagePizzaPizza.setVisibility(View.GONE);
            }
        });

    }

    private void checkAndIncrementSuccess() {
        if (atomicInteger.incrementAndGet() == countSuccess) {
            downloadAnimationStop();
            imagePizzaMan.setVisibility(View.VISIBLE);
            pushDataBtn.setEnabled(false);
        }
    }

    private void makeSuccessNumber() {
        countSuccess = aztecaList.size() + pizzaIFList.size() + camelotFoodList.size();
    }

    // animation methods -----------------------------------

    private void getDataAnimationStart() {
        animationTrance.setRepeatCount(Animation.INFINITE);
        imagePizzaGetData.startAnimation(animationTrance);
    }

    private void getDataAnimationStop() {
        imagePizzaGetData.clearAnimation();
        imagePizzaGetData.setVisibility(View.GONE);
    }

    private static void pushDataAnimationStart() {
        animationTrance.setRepeatCount(Animation.INFINITE);
        imagePizzaPushData.startAnimation(animationTrance);
    }

    private static void pushDataAnimationStop() {
        imagePizzaPushData.setVisibility(View.GONE);
        imagePizzaPushData.clearAnimation();
    }

    private void downloadAnimationStart() {
        animationRotate.setRepeatCount(Animation.INFINITE);
        imagePizzaDownload.startAnimation(animationRotate);
    }

    private static void downloadAnimationStop() {
        imagePizzaDownload.clearAnimation();
        imagePizzaDownload.setVisibility(View.GONE);
    }

    //-----------------------------------------------

    private void addDataToFireStore(ArrayList<Pizza> list, String nameCollection) {
        for(int i = 0; i < list.size(); i++) {

            db.collection(nameCollection).document(list.get(i).getTitle())
                    .set(list.get(i))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkAndIncrementSuccess();
                            Log.i(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });


        }
    }

    private static class AztecaPizza extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            aztecaList.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Document document;
            try {
                document = Jsoup.connect("http://azteca.if.ua/pizza/").get();
                elements = document.select("li.instock");
                for(int i = 0; i < elements.size(); i++) {
                    int id = i + 1;
                    String title = elements.get(i).select("h3").text();
                    String description = elements.get(i).select("em").text();
                    String diameter = elements.get(i).select("tr[style='height: 25px']")
                            .select("font[color='#124831']")
                            .text();
                    String diameterLarge = elements.get(i).select("tr[style='height: 25px']")
                            .select("font[color='#b80928']")
                            .text();
                    String priceStandart = elements.get(i).select("td[style='text-align: center; border-top: 0; border-right: 1px solid #DCD7C1; padding: 5px']")
                            .select("span.amount")
                            .text();
                    String priceLarge = elements.get(i).select("td[style='text-align: center; border-top: 0; padding: 5px']")
                            .select("span.amount")
                            .text();
                    String imageSrc = elements.get(i).select("img").attr("src");
                    // String imagePath = makeImage(imageSrc, Resources.AZTECA);


                    aztecaList.add(new Pizza(id, title, null, getDigits(priceStandart).concat(" грн."),
                            getDigits(priceLarge).concat(" грн."), getDigits(diameter).concat(" см."),
                            getDigits(diameterLarge).concat(" см."), description, imageSrc));

                    Log.i(TAG, title + "\n" + priceStandart + "\n" + priceLarge + "\n"
                            + description + "\n" + diameter + "\n" + diameterLarge
                            + "\n" + imageSrc);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i(TAG, aztecaList.size() + "");
        }
    }

    private static class PizzaIF extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pizzaIFList.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Document document;
            try{
                document = Jsoup.connect("http://pizza-if.com/").get();
                elements = document.select("div#main_content").select("div.mcb_item");
                for(int i = 0; i < elements.size(); i++) {
                    int id = i + 1;
                    String title = elements.get(i).select("div.mcbi_info")
                            .select("h3").text();
                    String description = elements.get(i).select("div.mcbi_info")
                            .select("p.m_i_contain").text();
                    String diameterAndWeight = elements.get(i).select("div.mcbi_info")
                            .select("p[class='m_i_size mis_select']")
                            .select("span.mis_lift").text();
                    String priceStandart = elements.get(i)
                            .select("div.mcbi_info")
                            .select("div[class='PriceBackground']")
                            .select("span.mis_right").text();
                    String imgSrc = elements.get(i).select("img")
                            .attr("src");
                    // String imagePath = makeImage(imgSrc, Resources.PIZZAIF);

                    pizzaIFList.add(new Pizza(id, title, parserPizzaIfWeight(diameterAndWeight), priceStandart,
                            null, parsePizzaIfDiameter(diameterAndWeight), null,
                            description, imgSrc));

                    Log.i(TAG, title + "\n" + description
                            + "\n" + parsePizzaIfDiameter(diameterAndWeight) + "\n" + priceStandart
                            + "\n" + imgSrc + "\n" + parserPizzaIfWeight(diameterAndWeight));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.i(TAG, pizzaIFList.size() + "");
        }
    }

    private static class CamelotFood extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            camelotFoodList.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document document;
            try {

                document = Jsoup.connect("http://camelot-food.com/menu/pizza").get();
                elements = document.select("div#advantages2").select("div.product");

                for(int i = 0; i < elements.size(); i++) {
                    int id = i + 1;
                    String title = elements.get(i).select("div.text")
                            .select("h3").text();
                    String description = elements.get(i).select("div.text")
                            .select("div.description").text();
                    String diameter = elements.get(i).select("div.text")
                            .select("p[style='text-align: center; font-size: 15px; color: #7F7F7F; font-weight: 500;']")
                            .select("span").text();
                    String priceStandart = elements.get(i).select("div.text")
                            .select("div.input-group")
                            .select("p[class='price sale-price-block']").text();
                    String imgSrc = elements.get(i).select("img")
                            .attr("src");

                    diameter = getDigits(diameter).concat(" см.");
                    imgSrc = "http://camelot-food.com".concat(imgSrc);

                    camelotFoodList.add(new Pizza(id, title, null, priceStandart,
                            null, diameter, null, description,
                            imgSrc));

                    Log.i(TAG, title + "\n" + description + "\n"
                            + diameter + "\n" + priceStandart + "\n" + imgSrc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // animation

            downloadAnimationStop();

            pushDataBtn.setEnabled(true);
            getDataBtn.setEnabled(false);
            imagePizzaPizza.setVisibility(View.VISIBLE);

            pushDataAnimationStart();

            Log.i(TAG, camelotFoodList.size() + "");
        }
    }

    private static String parserPizzaIfWeight(String string) {
        String result = null;
        if(string.length() > 10) {
            result = getDigits(string.substring(7,string.length()-1));
            result = result.concat(" гр.");
        }
        return result;
    }

    private static String getDigits(String string) {
        String result = "";
        char[] array = string.toCharArray();

        for(int i = 0; i < array.length; i++) {
            if(Character.isDigit(array[i])) {
                result = result.concat(String.valueOf(array[i]));
            }
        }
        return result;
    }

    private static String parsePizzaIfDiameter(String string) {
        String result = null;
        if(string.length() >= 6) {
            result = string.substring(0, 5);
        }
        return result;
    }

    private String makeImage(String src, String folderName) throws IOException {

        String imagePath;

        //Exctract the name of the image from the src attribute
        int indexName = src.lastIndexOf("/");

        if (indexName == src.length()) {
            src = src.substring(1, indexName);
        }
        indexName = src.lastIndexOf("/");
        String name = src.substring(indexName + 1, src.length());

        // Log.i(TAG, name);

        //Open a URL Stream
        URL url = new URL(src);
        InputStream in = url.openStream();

        imagePath = createFolder(folderName) + File.separator + name;
        OutputStream out = new BufferedOutputStream(new FileOutputStream(imagePath));

        for (int i; (i = in.read()) != -1;)
            out.write(i);

        out.close();
        in.close();

        return imagePath;
    }

    private String createFolder(String folderName) {
        File folder = new File(getFilesDir() +
                File.separator + folderName);

        // Log.i(TAG, folder.toString());

        boolean success = true;

        if (!folder.exists())
            success = folder.mkdirs();

        if (success)
            Log.i(TAG, " FOLDER CREATED");
        else
            Log.i(TAG, " FOLDER NOT CREATED");

        return folder.toString();
    }
}