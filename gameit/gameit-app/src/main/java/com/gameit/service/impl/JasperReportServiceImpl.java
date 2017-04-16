package com.gameit.service.impl;

import com.gameit.model.UserGameOrder;
import com.gameit.service.JasperReportService;
import com.gameit.util.JasperReportsTemplates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.group.CustomGroupBuilder;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.definition.ReportParameters;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@Service
public class JasperReportServiceImpl implements JasperReportService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    @Override
    public JasperReportBuilder createUsersReport() {
        JasperReportBuilder report = DynamicReports.report();
        try {
            report
                    .columns(
                            Columns.column("User Id", "id", DataTypes.longType()),
                            Columns.column("Email ", "email", DataTypes.stringType()),
                            Columns.column("Username", "username", DataTypes.stringType()))
                    .title(
                            Components.text("SimpleReportExample")
                                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                    .pageFooter(Components.pageXofY())
                    .setDataSource("SELECT id, email, username FROM appuser",
                            dataSource.getConnection());
            return report;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JasperReportBuilder createGamesReport() {
        TextColumnBuilder<String> categoryColumn = col.column("Game Category", exp.text(""));
        TextColumnBuilder<String> nameColumn = col.column("Game Name", "name", type.stringType());
        TextColumnBuilder<Integer> gameReleaseYearColumn = col.column("Game Release Year", "release_year", type.integerType());
        TextColumnBuilder<BigDecimal> gamePriceColumn = col.column("Game Price ($)", "game_price", type.bigDecimalType());

        Bar3DChartBuilder itemChart = cht.bar3DChart()
                .setTitle("Game price")
                .setCategory(nameColumn)
                .addSerie(cht.serie(gamePriceColumn))
                .setShowLabels(true)
                .setPositionType(ComponentPositionType.FLOAT)
                .setShowValues(true);

        CustomGroupBuilder categoryGroup = grp.group(new GameCategoryExpression())
                .groupByDataType()
                .setHeaderLayout(GroupHeaderLayout.EMPTY)
                .setPadding(0);

        JasperReportBuilder report = DynamicReports.report();

        try {
            report
                    .setTemplate(JasperReportsTemplates.reportTemplate)
                    .setSubtotalStyle(stl.style(JasperReportsTemplates.boldStyle))
                    .fields(
                            field("category", type.stringType()))
                    .columns(
                            categoryColumn, nameColumn, gameReleaseYearColumn, gamePriceColumn)
                    .groupBy(categoryGroup)
                    .title(JasperReportsTemplates.createTitleComponent("Category Report"))
                    .pageFooter(cmp.pageXofY()
                            .setStyle(stl.style()
                                    .setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE)
                                    .setTopBorder(stl.pen1Point())))
                    .summary(itemChart)
                    .setDataSource("SELECT * FROM game", dataSource.getConnection());
            return report;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JasperReportBuilder createOrderReport(UserGameOrder order) {
        JasperReportBuilder report = DynamicReports.report();
        try {
            report
                    .columns(
                            Columns.column("Order Id", "payment_processor_charge_id", DataTypes.stringType()),
                            Columns.column("Game Name", "name", DataTypes.stringType()),
                            Columns.column("Game Price ", "game_price", DataTypes.doubleType()))
                    .title(
                            Components.text("Order Report")
                                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                    .pageFooter(Components.pageXofY())
                    .setDataSource("SELECT u.stripe_order_id, g.name, g.game_price FROM user_game_order as u,game as g" +
                                    " where u.game_id = g.game_id AND u.stripe_order_id=" + order.getPaymentProcessorChargeId(),
                            dataSource.getConnection());
            return report;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class GameCategoryExpression extends AbstractSimpleExpression<String> {
        private static final long serialVersionUID = 1L;

        @Override
        public String evaluate(ReportParameters reportParameters) {
            return type.stringType().valueToString("category", reportParameters);
        }
    }
}
