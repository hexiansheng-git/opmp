package com.hhwy.pm.qqch.sgch.sche.service;

import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;

public interface IQqchScheService {
    QqchScheDTO list(QqchScheDTO dto);

    void save(QqchScheDTO dto);

    QqchScheDTO getItems();
}
