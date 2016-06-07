package net.wendal.nutzbook;

import org.nutz.integration.shiro.ShiroSessionProvider;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * @author zhangjinci
 * @version 2016-6-6
 */
@SetupBy(value = MainSetup.class)
@IocBy(type = ComboIocProvider.class, args = {"*js", "conf/ioc/",
        "*anno", "net.wendal.nutzbook",
        "*tx",
        "*org.nutz.integration.quartz.QuartzIocLoader"})
@Ok("json:full")
@Fail("jsp:/500")
@Localization(value = "conf/msg/", defaultLocalizationKey = "zh-CN")
@ChainBy(args = "conf/mvc/nutzbook-mvc-chain.js")
@SessionBy(ShiroSessionProvider.class)
@Modules(scanPackage = true)
public class MainModule {
}
