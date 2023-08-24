package com.example.androiddoexercise;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Topic {
    boolean is_single = true;
    String topic;
    String A,B,C,D;
    String answer;
    String analyze;
    Topic(String t,String a,String b,String c,String d,String ans,String analy) {
        topic = t;
        A = a;
        B = b;
        C = c;
        D = d;
        answer = ans;
        analyze = analy;
    }
    void SetSingle(boolean new_is_single) {
        is_single = new_is_single;
    }
    void SetTopic(String new_topic) {
        topic = new_topic;
    }
    void SetAnswer(String new_answer) {
        answer = new_answer;
    }
    void SetAnaluze(String new_analuze) {
        analyze = new_analuze;
    }
    void SetA(String new_A) {
        A = new_A;
    }
    void SetB(String new_B) {
        B = new_B;
    }
    void SetC(String new_C) {
        C = new_C;
    }
    void SetD(String new_D) {
        D = new_D;
    }
}

public class DoExercise extends AppCompatActivity {
    static ArrayList<Topic> topics;
    boolean is_loaded_topic = false;

    static int default_color = -10006364;
    static int but_a,but_b,but_c,but_d;
    static Topic cur_topic;
    static int cur_pos = 0;


    /*************************************************************************
     *  加载题目信息
     *************************************************************************/
    void LoadTopic() {
        topics = new ArrayList<Topic>();
        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            //得到资源中的Raw数据流
            inputStream = getResources().openRawResource(R.raw.sigle_1);
            reader = new InputStreamReader(inputStream);// 字符流
            bufferedReader = new BufferedReader(reader); //缓冲流
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                String t,a,b,c,d,ans,analy;
                t = tmp;
                a = bufferedReader.readLine();
                b = bufferedReader.readLine();
                c = bufferedReader.readLine();
                d = bufferedReader.readLine();
                ans = bufferedReader.readLine();
                analy = bufferedReader.readLine();

                if (!analy.equals("")) {
                    // tmp吃掉多余的空行
                    tmp = bufferedReader.readLine();
                }
                Topic topic = new Topic(t,a,b,c,d,ans,analy);
                topics.add(topic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        is_loaded_topic = true;
    }


    /*************************************************************************
     *  点击器,点击的时候触发一次
     *************************************************************************/
    public void ShowTopic(Topic topic) {
        TextView topic_text = findViewById(R.id.topic_text);
        TextView anaswer_text = findViewById(R.id.answer_text);
        TextView analyze_text = findViewById(R.id.analyze_text);

        Button A_but = findViewById(R.id.A_button);
        Button B_but = findViewById(R.id.B_button);
        Button C_but = findViewById(R.id.C_button);
        Button D_but = findViewById(R.id.D_button);

        A_but.setText(topic.A);
        A_but.setBackgroundColor(default_color);
        B_but.setText(topic.B);
        B_but.setBackgroundColor(default_color);
        C_but.setText(topic.C);
        C_but.setBackgroundColor(default_color);
        D_but.setText(topic.D);
        D_but.setBackgroundColor(default_color);

        topic_text.setText(topic.topic);
        anaswer_text.setText(topic.answer);
        analyze_text.setText(topic.analyze);

        anaswer_text.setVisibility(View.GONE);
        analyze_text.setVisibility(View.GONE);

        cur_topic = topic;
    }

    public static void NextTopic() {
        cur_pos ++;
        cur_topic = topics.get(cur_pos);
    }
    public static void PreTopic() {
        cur_pos --;
        cur_topic = topics.get(cur_pos);
    }
    // 退出答题时统计信息
    void Statistics() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercise);
        Button A_but = findViewById(R.id.A_button);
        Button B_but = findViewById(R.id.B_button);
        Button C_but = findViewById(R.id.C_button);
        Button D_but = findViewById(R.id.D_button);

        DoExerciseListener do_exercise_listener = new DoExerciseListener(findViewById(R.id.answer_text),findViewById(R.id.analyze_text),this);
        A_but.setOnClickListener(do_exercise_listener);
        B_but.setOnClickListener(do_exercise_listener);
        C_but.setOnClickListener(do_exercise_listener);
        D_but.setOnClickListener(do_exercise_listener);

        ArrayList<Topic> topic_arr= new ArrayList<Topic>();


        // 如果题目没有被加载过,就加载题目
        if (is_loaded_topic == false) {
            LoadTopic();
        }
        ShowTopic(topics.get(0));

        but_a = findViewById(R.id.A_button).getId();
        but_b = findViewById(R.id.B_button).getId();
        but_c = findViewById(R.id.C_button).getId();
        but_d = findViewById(R.id.D_button).getId();


    }

    /*************************************************************************
    *  点击器,点击的时候触发一次
    *************************************************************************/
    static class DoExerciseListener implements View.OnClickListener {
        static int default_color = -10006364;
        static int choose_color = -16737793;
        static int bad_color = 1694433280;
        private DoExercise DE;
        HashMap<Integer, Boolean> button_count;

        TextView anaswer_text;
        TextView analyze_text;
        DoExerciseListener(TextView anaswer,TextView analyze,DoExercise activity) {
            button_count = new HashMap<Integer,Boolean>();
            anaswer_text = anaswer;
            analyze_text = analyze;
            DE = activity;
        }


        @Override
        public void onClick(View view) {
            Button but = (Button) view;

            // 默认隐藏
            anaswer_text.setVisibility(View.GONE);
            analyze_text.setVisibility(View.GONE);

            // 如果hashmap中没有这个按钮的map
            if (!button_count.containsKey(but.getId())) {
                button_count.put(but.getId(),false);
            }

            Boolean is_choose = button_count.get(but.getId());
            if (is_choose == true) {
                button_count.put(but.getId(),false);
                but.setBackgroundColor(default_color);
            }
            else {
                button_count.put(but.getId(),true);
                but.setBackgroundColor(choose_color);
            }



            String choose_ans = new String();
            if (but.getId() == DoExercise.but_a) {
                choose_ans = "A";
            }
            if (but.getId() == DoExercise.but_b) {
                choose_ans = "B";
            }
            if (but.getId() == DoExercise.but_c) {
                choose_ans = "C";
            }
            if (but.getId() == DoExercise.but_d) {
                choose_ans = "D";
            }

            // 答案正确
            if (choose_ans.equals(DoExercise.cur_topic.answer)) {
                DoExercise.NextTopic();
                DE.ShowTopic(cur_topic);
            }
            // 答案错误
            else {
                but.setBackgroundColor(bad_color);
                anaswer_text.setVisibility(View.VISIBLE);
                analyze_text.setVisibility(View.VISIBLE);
            }

        }
    }
}
