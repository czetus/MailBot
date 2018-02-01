package com.czetus.main;

public class EmailAccount
{
    private String smtpAddres;

    public EmailAccount(String smptAddr)
    {
        this.smtpAddres = smptAddr;
    }

    public String getSmtpAddres()
    {
        return smtpAddres;
    }

    public void setSmtpAddres(String smtpAddres)
    {
        this.smtpAddres = smtpAddres;
    }

}
