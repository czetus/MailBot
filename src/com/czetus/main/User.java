package com.czetus.main;

public class User
{
    private String name;
    private String password;
    private EmailAccount emailAccount;

    public User(String name, String password, EmailAccount emailAccount){
        this.name = name;
        this.password = password;
        this.emailAccount = emailAccount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public EmailAccount getEmailAccount()
    {
        return emailAccount;
    }

    public void setEmailAccount(EmailAccount emailAccount)
    {
        this.emailAccount = emailAccount;
    }
}
