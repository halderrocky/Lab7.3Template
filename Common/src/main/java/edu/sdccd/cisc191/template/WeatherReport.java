package edu.sdccd.cisc191.template;

import java.io.Serializable;
import java.util.Date;

class WeatherReport implements Serializable {
    private Date reportDate;
    private String reportDetails;

    public WeatherReport(Date reportDate, String reportDetails) {
        this.reportDate = reportDate;
        this.reportDetails = reportDetails;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    @Override
    public String toString() {
        return "Weather Report on " + reportDate + ": " + reportDetails;
    }
}

