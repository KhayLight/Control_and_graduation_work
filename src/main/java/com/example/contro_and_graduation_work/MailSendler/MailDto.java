package com.example.contro_and_graduation_work.MailSendler;

import lombok.Data;

@Data
public class MailDto {
    private String email = null;
    private XmlOrCsv xmlOrCsv = null;
}
