package com.mondyxue.xrouter.demo.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.constant.ResultCode;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.R;
import com.mondyxue.xrouter.demo.constant.Extras;
import com.mondyxue.xrouter.demo.data.UserInfo;
import com.mondyxue.xrouter.demo.navigator.DemoNavigator;
import com.mondyxue.xrouter.demo.service.UserService;

/**
 * @author Mondy <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = DemoNavigator._LoginFragment, extras = RouteType.TitlebarFragment)
public class LoginFragment extends BaseFragment{

    private EditText mEtUserId;
    @Override protected int getRootLayout(){
        return R.layout.fragment_login;
    }

    @Override protected void initView(View rootView){

        mEtUserId = rootView.findViewById(R.id.et_userId);

        rootView.findViewById(R.id.btn_login)
                .setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){

                        //create a navigator with interfaces
                        DemoNavigator navigator = XRouter.getRouter()
                                                         .create(DemoNavigator.class);

                        //get a service by navigator
                        UserService userService = navigator.getUserService();

                        long userId;

                        try{
                            userId = Long.parseLong(mEtUserId.getText().toString());
                        }catch(NumberFormatException e){
                            navigator.getLogger().e("LoginFragment", "parsing userId", Log.getStackTraceString(e));
                            Toast.makeText(getActivity(), "please input a valid userId", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        userService.login(userId);

                        UserInfo userInfo = userService.getUserInfo();

                        Toast.makeText(getActivity(), "login success: " + userInfo.toString(), Toast.LENGTH_SHORT).show();

                        //set result for startActivityForResult
                        Intent data = new Intent().putExtra(Extras.UserInfo, userInfo);
                        getActivity().setResult(ResultCode.SUCCESS, data);
                        getActivity().finish();

                    }
                });

    }

    @Override protected void onBackPressed(){
        XRouter.getRouter()
               .create(DemoNavigator.class)
               .toMainActivity(Extras.Action_Back);
    }

}
