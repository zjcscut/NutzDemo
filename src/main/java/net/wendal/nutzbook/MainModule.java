package net.wendal.nutzbook;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * @author zhangjinci
 * @version 2016-6-6
 */
@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "conf/ioc/",
        "*anno", "net.wendal.nutzbook",
        "*tx"})
@Modules(scanPackage = true)
public class MainModule {
}
