package com.mondyxue.xrouter.demo.login.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mondyxue.xrouter.XRouter;
import com.mondyxue.xrouter.constant.ResultCode;
import com.mondyxue.xrouter.constant.RouteType;
import com.mondyxue.xrouter.demo.api.constant.Extras;
import com.mondyxue.xrouter.demo.api.data.UserInfo;
import com.mondyxue.xrouter.demo.api.navigator.BaseNavigator;
import com.mondyxue.xrouter.demo.api.navigator.DemoNavigator;
import com.mondyxue.xrouter.demo.api.navigator.LoginNavigator;
import com.mondyxue.xrouter.demo.api.service.Logger;
import com.mondyxue.xrouter.demo.api.service.UserService;
import com.mondyxue.xrouter.demo.base.ui.fragment.BaseFragment;
import com.mondyxue.xrouter.demo.login.R;

/**
 * @author MondyXue <a href="mailto:mondyxue@gmail.com">E-Mail</a>
 */
@Route(path = LoginNavigator._LoginFragment, extras = RouteType.TitlebarFragment)
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

                        //get a service by navigator
                        UserService userService = XRouter.getRouter()
                                                         .create(LoginNavigator.class)
                                                         .getUserService();

                        long userId;

                        try{

                            userId = Long.parseLong(mEtUserId.getText().toString());

                        }catch(NumberFormatException e){

                            //get a service by navigator
                            Logger logger = XRouter.getRouter()
                                                   .create(BaseNavigator.class)
                                                   .getLogger();

                            logger.e("LoginFragment", "parsing userId", Log.getStackTraceString(e));

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
