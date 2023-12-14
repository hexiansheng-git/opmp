package com.hhwy.sp.utils;

import com.alibaba.excel.util.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩包
 *
 * @date 2023-12-13
 * @author lcf
 */
public class ZipUtils {
    /**
     * 压缩吧
     *
     * @param files
     * @param response
     */
    public static void zipFile(List<File> files, HttpServletResponse response){
        String zipFileName= "设备二维码";
        BufferedOutputStream bos = null ;
        FileInputStream in = null;
        ZipOutputStream out = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + zipFileName + ".zip");
            out = new ZipOutputStream(bos);
            for(File file :  files) {
                if(!file.exists()){
                    continue;
                }
                ZipEntry zEntry = new ZipEntry(file.getName());
                out.putNextEntry(zEntry);
                in = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int read = 0;
                while((read = in.read(buffer)) != -1){
                    out.write(buffer, 0, read);
                }

                try {
                    if(in!=null)
                        in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("异常拉！");
        }finally {

            try {
                if(in!=null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(in!=null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(File file :  files) {
                FileUtils.delete(file);
            }
        }
    }
}
