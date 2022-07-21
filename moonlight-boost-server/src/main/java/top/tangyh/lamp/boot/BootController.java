package top.tangyh.lamp.boot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;

/*
 * Created by Knight-ZXW on 2022/7/18
 * email: nimdanoob@163.com
 */
@RestController
public class BootController {

    @GetMapping("healthCheck")
    public R<Boolean> healthCheck(){
        return R.success();
    }


}
