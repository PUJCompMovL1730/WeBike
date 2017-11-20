package webike.webike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import webike.webike.logic.Message;

public class ReadMessageActivity extends AppCompatActivity {

    TextView subject;
    TextView from;
    TextView to;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);

        subject = (TextView) findViewById(R.id.subject_textView);
        from = (TextView) findViewById(R.id.from_textView);
        to = (TextView) findViewById(R.id.to_textView);
        message = (TextView) findViewById(R.id.message_textView);
        message.setMovementMethod(new ScrollingMovementMethod());

        Bundle bundle = getIntent().getExtras();
        Message msg = new Message();
        if( bundle != null ){
            msg = (Message) bundle.get("msg");
        }

        subject.setText(msg.getSubject());
        from.setText("De: "+msg.getSender());
        to.setText("Para: "+msg.getReceiver());
        message.setText(msg.getMsg());
    }
}
