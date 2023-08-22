package com.example.androiddoexercise;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Topic {
    boolean is_single = true;
    String topic;
    String A,B,C,D;
    String answer;
    String analyze;

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
    Map<Integer,Integer> is_choose;
    void LoadTopic() {

    }
    void ShowTopic(Topic topic) {
        TextView topic_text = findViewById(R.id.topic_text);
        TextView anaswer_text = findViewById(R.id.answer_text);
        TextView analyze_text = findViewById(R.id.analyze_text);

        Button A_but = findViewById(R.id.A_button);
        Button B_but = findViewById(R.id.B_button);
        Button C_but = findViewById(R.id.C_button);
        Button D_but = findViewById(R.id.D_button);

        A_but.setText(topic.A);
        B_but.setText(topic.B);
        C_but.setText(topic.C);
        D_but.setText(topic.D);

        topic_text.setText(topic.topic);
        anaswer_text.setText(topic.answer);
        analyze_text.setText(topic.analyze);

        anaswer_text.setVisibility(View.GONE);
        analyze_text.setVisibility(View.GONE);

    }

    void NextTopic() {

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

        DoExerciseListener do_exercise_listener = new DoExerciseListener(findViewById(R.id.answer_text),findViewById(R.id.analyze_text));
        A_but.setOnClickListener(do_exercise_listener);
        B_but.setOnClickListener(do_exercise_listener);
        C_but.setOnClickListener(do_exercise_listener);
        D_but.setOnClickListener(do_exercise_listener);

        ArrayList<Topic> topic_arr= new ArrayList<Topic>();

        for (int i = 0;i < 10; ++i) {
            Topic topic = new Topic();
            topic.SetTopic("这个是题目" + i);
            topic.SetA("这是选项A" + i);
            topic.SetB("这是选项B" + i);
            topic.SetC("这是选项C" + i);
            topic.SetD("这是选项D" + i);

            topic.SetAnswer("这个是答案:ABCD");
            String tmp = "";
            for(int j = 0;j < 100; ++j) {
                tmp += i;
            }
            topic.SetAnaluze("这个是解析" + tmp);
            topic_arr.add(topic);
        }

        ShowTopic(topic_arr.get(0));
    }

    static class DoExerciseListener implements View.OnClickListener {
        static int default_color = -10006364;
        static int choose_color = -16737793;

        HashMap<Integer, Boolean> button_count;

        TextView anaswer_text;
        TextView analyze_text;
        DoExerciseListener(TextView anaswer,TextView analyze) {
            button_count = new HashMap<Integer,Boolean>();
            anaswer_text = anaswer;
            analyze_text = analyze;
        }


        @Override
        public void onClick(View view) {
            Button but = (Button) view;

            // 如果hashmap中没有这个按钮的map
            if (!button_count.containsKey(but.getId())) {
                button_count.put(but.getId(),false);
            }

            Boolean is_choose = button_count.get(but.getId());
            if (is_choose == true) {
                button_count.put(but.getId(),false);
                but.setBackgroundColor(default_color);
                but.setText("未选中");
            }
            else {
                button_count.put(but.getId(),true);
                but.setBackgroundColor(choose_color);
                but.setText("选中");
            }
            int count = 0;
            for (Boolean tmp : button_count.values()) {
                if (tmp == true) {
                    count ++;
                }
            }

            if (count == 4) {
                anaswer_text.setVisibility(View.VISIBLE);
                analyze_text.setVisibility(View.VISIBLE);
            }
            else {
                anaswer_text.setVisibility(View.GONE);
                analyze_text.setVisibility(View.GONE);
            }

        }
    }
}
