package com.msgfoundation.MSGFTreasury.model;

import lombok.Data;


@Data
public class Payment {
    private String processId;
    private String date;
    private String coupleName1;
    private String coupleName2;
    private String coupleEmail1;
    private String coupleEmail2;
    private String housePrices;
    private String quotaValue;

}
