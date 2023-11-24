package com.hhwy.pm.word.export.service;

import com.deepoove.poi.data.PictureRenderData;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ExportWordService {
    void exportProjectQqch(HttpServletResponse response) throws UnsupportedEncodingException;

    List<PictureRenderData> getPictureRenderDataList(String fileGroupId);
}
