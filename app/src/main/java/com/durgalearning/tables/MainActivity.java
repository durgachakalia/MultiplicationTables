package com.durgalearning.tables;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.graphics.Typeface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends Activity {
    private TextToSpeech tts;
    private boolean ttsReady = false;
    private LinearLayout root;
    private int currentTable = 0;
    private final int purple = Color.rgb(67, 56, 202);
    private final int ink = Color.rgb(31, 41, 55);
    private final int[] palette = {
        0xFFEDE9FE, 0xFFFFF1F2, 0xFFECFDF5, 0xFFFFF7ED, 0xFFEFF6FF,
        0xFFFDF2F8, 0xFFFEFCE8, 0xFFF0FDFA, 0xFFF5F3FF, 0xFFF0F9FF
    };

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(purple);
        getWindow().setNavigationBarColor(Color.rgb(36, 27, 85));
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(new Locale("en", "IN"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    tts.setLanguage(Locale.US);
                tts.setSpeechRate(0.88f);
                ttsReady = true;
            }
        });
        showHome();
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    private int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private LinearLayout page() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(18), dp(18), dp(24));
        root.setBackgroundColor(Color.rgb(248, 250, 255));
        scroll.addView(root);
        setContentView(scroll);
        return root;
    }

    private void addHeader(String subtitle) {
        TextView title = text("✖  CHAKALIA TABLES", 25, Color.WHITE, true);
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setPadding(dp(18), dp(18), dp(18), dp(5));
        TextView sub = text(subtitle, 14, 0xFFE0E7FF, false);
        sub.setGravity(Gravity.CENTER_VERTICAL);
        sub.setPadding(dp(18), 0, dp(18), dp(18));
        LinearLayout banner = new LinearLayout(this);
        banner.setOrientation(LinearLayout.VERTICAL);
        banner.setBackground(shape(purple, 22));
        banner.addView(title);
        banner.addView(sub);
        root.addView(banner, new LinearLayout.LayoutParams(-1, -2));
    }

    private void showHome() {
        page();
        addHeader("Learn, listen and practise multiplication!");
        TextView instruction = text("Choose a table to get started", 18, ink, true);
        instruction.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        instruction.setPadding(dp(4), dp(22), 0, dp(12));
        root.addView(instruction);

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(2);
        grid.setUseDefaultMargins(false);
        for (int n = 1; n <= 20; n++) {
            final int number = n;
            TextView tile = text("Table of " + n + "\nTap to learn", 17, ink, true);
            tile.setBackground(shape(palette[(n - 1) % palette.length], 18));
            tile.setPadding(dp(8), dp(16), dp(8), dp(16));
            tile.setElevation(dp(2));
            tile.setOnClickListener(v -> showTable(number));
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 0;
            lp.height = dp(104);
            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            lp.setMargins(dp(5), dp(5), dp(5), dp(5));
            grid.addView(tile, lp);
        }
        root.addView(grid);
        TextView footer = text("Every table runs from ×1 to ×20. Tap a line to hear it spoken aloud.", 14, 0xFF64748B, false);
        footer.setPadding(0, dp(18), 0, dp(4));
        root.addView(footer);
    }

    private void showTable(int number) {
        currentTable = number;
        page();
        addHeader("Tap any line from 1 to 20 and listen");
        Button back = button("←  All tables", 0xFFE0E7FF, purple);
        back.setOnClickListener(v -> showHome());
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-2, dp(46));
        bp.setMargins(0, dp(14), 0, dp(10));
        root.addView(back, bp);

        TextView heading = text("Table of " + number, 26, ink, true);
        heading.setPadding(0, dp(8), 0, dp(12));
        root.addView(heading);

        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        Button speakAll = button("▶  Read full table", purple, Color.WHITE);
        Button stop = button("■  Stop", 0xFFFFE4E6, 0xFFBE123C);
        LinearLayout.LayoutParams half = new LinearLayout.LayoutParams(0, dp(48), 1);
        half.setMargins(dp(3), 0, dp(3), dp(12));
        actions.addView(speakAll, half);
        actions.addView(stop, half);
        root.addView(actions);
        speakAll.setOnClickListener(v -> speakFullTable(number));
        stop.setOnClickListener(v -> { if (tts != null) tts.stop(); });

        for (int i = 1; i <= 20; i++) {
            final int multiplier = i;
            int result = number * i;
            TextView row = text(number + "  ×  " + i + "  =  " + result, 21, ink, true);
            row.setBackground(shape(palette[(i - 1) % palette.length], 16));
            row.setPadding(dp(10), dp(15), dp(10), dp(15));
            row.setElevation(dp(1));
            row.setContentDescription("Speak: " + number + " times " + i + " equals " + result);
            row.setOnClickListener(v -> speak(spokenEquation(number, multiplier)));
            LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(-1, -2);
            rp.setMargins(0, dp(4), 0, dp(4));
            root.addView(row, rp);
        }
        TextView hint = text("Tip: You can tap each sum as many times as you like.", 13, 0xFF64748B, false);
        hint.setPadding(0, dp(12), 0, 0);
        root.addView(hint);
    }

    private Button button(String label, int background, int foreground) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextSize(14);
        b.setTextColor(foreground);
        b.setAllCaps(false);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setBackground(shape(background, 14));
        return b;
    }

    private void speak(String words) {
        if (tts != null && ttsReady) {
            tts.stop();
            tts.speak(words, TextToSpeech.QUEUE_FLUSH, null, "table_line");
        }
    }

    private void speakFullTable(int number) {
        StringBuilder words = new StringBuilder("The table of ").append(numberWords(number)).append(". ");
        for (int i = 1; i <= 20; i++)
            words.append(spokenEquation(number, i)).append(". ");
        speak(words.toString());
    }

    // Speaks equations in the familiar school-recitation style:
    // "Two ones are two", "Five tens are fifty".
    private String spokenEquation(int table, int multiplier) {
        int product = table * multiplier;
        if (table == 1) {
            return "One times " + numberWords(multiplier) + " is " + numberWords(product);
        }
        String multiplierWord = numberWords(multiplier);
        String plural = multiplier == 6 ? "sixes" : multiplierWord + "s";
        return numberWords(table) + " " + plural + " are " + numberWords(product);
    }

    private String numberWords(int number) {
        String[] ones = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
        if (number < 20) return ones[number];
        if (number < 100) {
            int remainder = number % 10;
            return tens[number / 10] + (remainder == 0 ? "" : "-" + ones[remainder]);
        }
        if (number < 1000) {
            int remainder = number % 100;
            return ones[number / 100] + " hundred" + (remainder == 0 ? "" : " " + numberWords(remainder));
        }
        return String.valueOf(number);
    }

    @Override protected void onDestroy() {
        if (tts != null) { tts.stop(); tts.shutdown(); }
        super.onDestroy();
    }
}
