package bluetoothnpc.aaronknobloch.com.grizzlycolors;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    public final String TAG = "GrizzlyColorsDB";
    SeekBar redBar;
    SeekBar greenBar;
    SeekBar blueBar;
    SeekBar alphaBar;

    class SeekBarHandle implements SeekBar.OnSeekBarChangeListener
    {
        TextView colorSwatch;

        public SeekBarHandle()
        {
            colorSwatch = (TextView) findViewById(R.id.colorSwatch);
        }

        private int colorValuesToInt(int red, int green, int blue, int alpha)
        {
            int color = 0;

            // create color by bit shifting colors to appropriate places
            color += alpha << 24;
            color += red << 16;
            color += green << 8;
            color += blue;

            return color;
        }

        private int findAppropriateTextColor(int red, int green, int blue, int alpha)
        {
            int luminosity = (int) (red * 0.2126
                    + green * 0.7152
                    + blue * 0.0722);

            // if the alpha is low or the luminosity is over 132,
            // the background is considered "bright" and text should be black
            if(alpha < 150 || luminosity > 132)
            {
                return Color.BLACK;
            }
            else
            {
                return Color.WHITE;
            }
        }

        private String colorToHex(int red, int green, int blue, int alpha)
        {
            String redHex = Integer.toHexString(red);
            String greenHex = Integer.toHexString(green);
            String blueHex = Integer.toHexString(blue);
            String alphaHex = Integer.toHexString(alpha);

            String hexRepresentation = "";

            // iterate through all color values and ensure their strings are "double digits"
            String[] colorHexValues = {alphaHex, redHex, greenHex, blueHex};
            for(String colorHex : colorHexValues)
            {
                while(colorHex.length() != 2)
                {
                    colorHex = "0" + colorHex;
                }

                hexRepresentation += colorHex;
            }

            return "#" + hexRepresentation.toUpperCase();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            int redValue = redBar.getProgress();
            int greenValue = greenBar.getProgress();
            int blueValue = blueBar.getProgress();
            int alphaValue = alphaBar.getProgress();

            int backgroundColor = colorValuesToInt(redValue, greenValue, blueValue, alphaValue);
            colorSwatch.setBackgroundColor(backgroundColor);

            int textColor = findAppropriateTextColor(redValue, greenValue, blueValue, alphaValue);
            colorSwatch.setTextColor(textColor);

            String hexValue = colorToHex(redValue, greenValue, blueValue, alphaValue);
            colorSwatch.setText(hexValue);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // member initialization
        redBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueBar = (SeekBar) findViewById(R.id.blueSeekBar);
        alphaBar = (SeekBar) findViewById(R.id.alphaSeekBar);

        // assign listener to seek bars
        SeekBarHandle seekBarHandler = new SeekBarHandle();

        redBar.setOnSeekBarChangeListener(seekBarHandler);
        greenBar.setOnSeekBarChangeListener(seekBarHandler);
        blueBar.setOnSeekBarChangeListener(seekBarHandler);
        alphaBar.setOnSeekBarChangeListener(seekBarHandler);

        // functionality for about button
        Button aboutButton = (Button) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast sourdough = Toast.makeText(getApplicationContext(),
                                                "Aaron Knobloch - February 26, 2017",
                                                Toast.LENGTH_SHORT);
                sourdough.show();
            }
        });
    }
}
