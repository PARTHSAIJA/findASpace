package com.findaspace.findaspace.feature;

import android.content.Context;

import com.findaspace.findaspace.main.emptyRoom.EmptyRoomActivity;
import com.findaspace.findaspace.main.login.LoginActivity;

import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class unitTest {
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
/* addition test*/
//    @Test
//    public void additionUnitTest() throws Exception{
//        int expected = 5;
//        int actual = LoginActivity.addition(3,2);
//        assertEquals(expected, actual);
//    }
/*Test from online*/
//    @RunWith(MockitoJUnitRunner.class)
//    public class LoginUnitTest {
//
//        private static final String FAKE_STRING = "Login was successful";
//
//        @Mock
//        Context mMockContext;
//
//        @Test
//        public void readStringFromContext_LocalizedString() {
//
//            LoginActivity myObjectUnderTest = new LoginActivity(mMockContext);
//
//            // ...when the string is returned from the object under test...
//            String result = myObjectUnderTest.validate("user","user");
//
//            // ...then the result should be the expected one.
//            assertThat(result, is(FAKE_STRING));
//        }

        /*From the testing online trying cross comparison between this and our code*/
//        public class LoginActivity extends AppCompatActivity {
//            Button btnLogin;
//            EditText userName;
//            EditText password;
//            @Override
//            protected void onCreate(@Nullable Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                setContentView(R.layout.login);
//                btnLogin=(Button)findViewById(R.id.btnLogin);
//                userName=(EditText)findViewById(R.id.edtUsername);
//                password=(EditText)findViewById(R.id.edtPassword);
//                btnLogin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        validate(userName.getText().toString(),password.getText().toString());
//                    }
//                });
//            }
//            public LoginActivity(Context context){
//
//            }
//            public String validate(String userName, String password)
//            {
//                if(userName.equals("user") && password.equals("user"))
//                    return "Login was successful";
//                else
//                    return "Invalid login!";
//            }

    }
//}
