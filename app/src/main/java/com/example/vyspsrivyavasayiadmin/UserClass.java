package com.example.vyspsrivyavasayiadmin;

import java.util.ArrayList;

public class UserClass {
    String name,mobile,email,area,area_code,status;

    UserClass(ArrayList<String> params){
        this.name = params.get(1);
        this.mobile = params.get(0);
        this.email = params.get(2);;
        this.area = params.get(4);;
        this.area_code = params.get(5);;
        this.status = params.get(3);;
    }

}
