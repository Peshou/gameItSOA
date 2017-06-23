package com.gameit.service;

import com.gameit.model.UserGameOrder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

public interface JasperReportService {

    JasperReportBuilder createUsersReport();

    JasperReportBuilder createGamesReport();

    JasperReportBuilder createOrderReport(UserGameOrder order);
}
