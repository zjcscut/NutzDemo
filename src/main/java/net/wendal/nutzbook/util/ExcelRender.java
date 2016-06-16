package net.wendal.nutzbook.util;

/**
 * @author zhangjinci
 * @version 2016/6/16 16:53
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whtr.eam.common.util.SystemContext;
import com.whtr.eam.common.util.jxls.TagRepository;
import com.whtr.eam.common.util.jxls.tag.JxlsUtilitys;
import com.whtr.eam.platform.dic.service.DicService;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * excel 渲染器
 *
 * @author luohancheng
 * @data 2016年5月3日
 *
 * @check 梁啟锵
 * @checkDate 2016-5-5
 *
 */
@IocBean
public class ExcelRender {

    private static final Logger log = LoggerFactory.getLogger(ExcelRender.class);
    private final static String MIME_EXCEL = "application/vnd.ms-excel";
    private final static String TAG_PREFIX = "TAG";
    private ThreadLocal<Map<String, Object>> root = new ThreadLocal<Map<>>();

    @Inject
    private DicService dicService;

    /**
     *
     * @param templateFileName 模板文件名称（WebContent/template文件夹下面）
     * @throws IOException
     * @throws ParsePropertyException
     * @throws InvalidFormatException
     */
    public void doExportExcel(String templateFileName) throws IOException, ParsePropertyException, InvalidFormatException {
        HttpServletResponse response = Mvcs.getResp();
        HttpServletRequest request = Mvcs.getReq();

        // 准备模板文件
        String path = Mvcs.getServletContext().getRealPath(
                SystemContext.TEMPLATE_EXCEL_PATH);
        File templateFile = new File(path, templateFileName);
        FileInputStream templateFileInputStream = new FileInputStream(templateFile);
        ByteArrayOutputStream destOutputStream = new ByteArrayOutputStream();

        log.debug("导出Excel，使用模板：{}", templateFile.toString());

        // 生成目标Excel文件
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateFileInputStream, initData())
                .write(destOutputStream);

        root.remove();	//清除数据

        byte[] content = destOutputStream.toByteArray();

        destOutputStream.close();
        templateFileInputStream.close();

        String fileName = templateFile.getName();
        String ua = request.getHeader("User-Agent");
        if (ua.indexOf("MSIE") != -1 || ua.indexOf("like Gecko") != -1) {	//like Gecko 是IE11
            // IE
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            // Chrome , Firefox
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        response.setHeader("Content-Disposition", "attachment;filename=\""
                + fileName + "\"");
        response.setHeader("Content-Length", String.valueOf(content.length));// byte单位
        response.setContentType(MIME_EXCEL);
        setNoCache(response);

        OutputStream os = response.getOutputStream();
        os.write(content);
        os.flush();

        os.close();
        templateFileInputStream.close();
        destOutputStream.close();
    }

    /**
     * 添加Excel导出数据Model.
     * @param name 引用Model变量名称，不能为保留名TAG
     * @param model Model实例对象
     */
    public void addExcelModel(String name, Object model) {
        Map<String, Object> models = root.get();
        if (models == null) {
            models = new HashMap<String, Object>();
            root.set(models);
        }
        models.put(name, model);
    }

    private Map<String, Object> initData() {
        TagRepository tr = TagRepository.getInstance();
        JxlsUtilitys utils = new JxlsUtilitys();
        utils.setDicService(dicService);
        tr.register("utils", utils);

        addExcelModel(TAG_PREFIX, tr.get());

        return root.get();
    }

    private void setNoCache(HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

}
