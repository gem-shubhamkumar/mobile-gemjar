package com.gemini.reporting;

class QuartzReporting {
    private String reportProduct;
    Suits_Details Suits_Details;

    public QuartzReporting(Suits_Details suits_Details) {
        this.reportProduct = "GEMJAR";
        this.Suits_Details = suits_Details;
    }

    public String getReportProduct() {
        return reportProduct;
    }

    public Suits_Details getSuits_Details() {
        return Suits_Details;
    }

    // Setter Methods

    public void setReportProduct(String reportProduct) {
        this.reportProduct = reportProduct;
    }

    public void setSuits_Details(Suits_Details Suits_DetailsObject) {
        this.Suits_Details = Suits_DetailsObject;
    }
}
